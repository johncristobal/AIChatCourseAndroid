# Views

Colocar espacio de margen solo en la barra de abajo
```kotlin
    modifier = modifier
    . . .
    .windowInsetsPadding(WindowInsets.navigationBars),
```

= = = = = = = = = = = = = = = = = = = = = = = = = = =

Para cargar una imagen desde una URL y local para la vista de previsualización

```
val isPreview = LocalInspectionMode.current
val model = if (isPreview) {
R.drawable.ai_icon // Local fallback resource for preview
} else {
urlString // Remote URL for production app
}
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)            
```

= = = = = = = = = = = = = = = = = = = = = = = = = = =

Modifier para circulo
    .clip(CircleShape)

= = = = = = = = = = = = = = = = = = = = = = = = = = =

animacion para visdualizar algo
```
    AnimatedVisibility(
    visible = selectedColor != Color.Transparent,
    enter = slideInVertically { -it } + fadeIn(),
    ) {
    PrimaryButton(
```

Pintar un grid de items con algunas props
```kotlin
LazyVerticalGrid(
    columns = Fixed(3),
    modifier = Modifier
    .weight(1f),
    contentPadding = PaddingValues(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
```

NOTA
checar codigo de **approot** para ver como se usa aprox el navigation
