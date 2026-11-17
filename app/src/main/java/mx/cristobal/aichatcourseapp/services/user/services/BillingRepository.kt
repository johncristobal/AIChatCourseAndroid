package mx.cristobal.aichatcourseapp.services.user.services

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : PurchasesUpdatedListener, BillingClientStateListener {

    private val scope = CoroutineScope(Dispatchers.IO)
    private companion object {
        private const val TAG = "BillingRepository"
    }

    private val _productDetails = MutableStateFlow<List<ProductDetails>>(emptyList())
    val productDetails: StateFlow<List<ProductDetails>> = _productDetails.asStateFlow()

    private val _purchases = MutableStateFlow<List<Purchase>>(emptyList())
    val purchases: StateFlow<List<Purchase>> = _purchases.asStateFlow()

    private val billingClient: BillingClient? by lazy {
        try {
            BillingClient.newBuilder(context)
                .setListener(this)
                .enablePendingPurchases(
                    PendingPurchasesParams.newBuilder()
                        .enableOneTimeProducts()
                        .build()
                )
                .build()
        } catch (t: Throwable) {
            Log.e(TAG, "Failed to initialize BillingClient (likely running in Compose Preview)", t)
            null
        }
    }

    fun startConnection() {
        val client = billingClient ?: return
        if (!client.isReady) {
            Log.d(TAG, "Starting connection to BillingClient...")
            client.startConnection(this)
        }
    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        Log.d(TAG, "onBillingSetupFinished code: ${billingResult.responseCode}, message: ${billingResult.debugMessage}")
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            // Query both Subscriptions and In-App products
            queryProducts(listOf("premium_monthly"), BillingClient.ProductType.SUBS)
            queryProducts(listOf("premium_monthly"), BillingClient.ProductType.INAPP)
            queryPurchases()
        }
    }

    override fun onBillingServiceDisconnected() {
        // Implement reconnection logic with exponential backoff if needed
        Log.w(TAG, "onBillingServiceDisconnected: Service disconnected")
    }

    fun queryProducts(
        productIds: List<String>,
        productType: String = BillingClient.ProductType.SUBS
    ) {
        val client = billingClient ?: return
        val productList = productIds.map { id ->
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(id)
                .setProductType(productType)
                .build()
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        client.queryProductDetailsAsync(params) { billingResult, queryResult ->
            Log.d(TAG, "queryProductDetailsAsync ($productType) code: ${billingResult.responseCode}, count: ${queryResult.productDetailsList?.size ?: 0}")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val currentList = _productDetails.value.toMutableList()
                val newItems = queryResult.productDetailsList ?: emptyList()
                val newIds = newItems.map { it.productId }.toSet()
                currentList.removeAll { it.productId in newIds }
                currentList.addAll(newItems)
                _productDetails.value = currentList
            }
        }
    }

    fun launchBillingFlow(activity: Activity, productDetails: ProductDetails) {
        val client = billingClient ?: return
        val offerToken = productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken.orEmpty()

        val productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(productDetails)
            .apply {
                if (offerToken.isNotEmpty()) setOfferToken(offerToken)
            }
            .build()

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(productDetailsParams))
            .build()

        val result = client.launchBillingFlow(activity, billingFlowParams)
        Log.d(TAG, "launchBillingFlow responseCode: ${result.responseCode}")
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        Log.d(TAG, "onPurchasesUpdated code: ${billingResult.responseCode}, purchases count: ${purchases?.size ?: 0}")
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else {
            Log.e(TAG, "Purchase failed/cancelled with code: ${billingResult.responseCode}, debugMessage: ${billingResult.debugMessage}")
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        val client = billingClient ?: return
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgeParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

                client.acknowledgePurchase(acknowledgeParams) { billingResult ->
                    Log.d(TAG, "acknowledgePurchase responseCode: ${billingResult.responseCode}")
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        // Grant feature to user locally / update state
                        queryPurchases()
                    }
                }
            } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
                Log.d(TAG, "Purchase state is PENDING. Waiting for user payment completion.")
            }
        }
    }

    fun queryPurchases() {
        val client = billingClient ?: return
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        client.queryPurchasesAsync(params) { billingResult, purchaseList ->
            Log.d(TAG, "queryPurchasesAsync code: ${billingResult.responseCode}, active purchases: ${purchaseList.size}")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                _purchases.value = purchaseList
            }
        }
    }

    fun endConnection() {
        val client = billingClient ?: return
        if (client.isReady) {
            client.endConnection()
        }
    }
}