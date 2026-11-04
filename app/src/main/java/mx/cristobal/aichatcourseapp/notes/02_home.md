# Home

creamos mocks con el model
```
companion object {
val mocks = listOf(
    AvatarModel(
        avatarId = "avatar_1",
```

OJO con Pager para carrusel
- coolocar la cards en primer lugar para que se vea el efecto de page
- state con items
- pagesize paradefinir espacio entre items

```
HorizontalPager(state = pagerFeaturedState) { page ->
    val item = items[page]
```

- pageState ayuda a saber estado del pager
```
val selectedColor = if (pagerFeaturedState.currentPage == index) {
```

====================

Gradientes, ver codigo paramas demos
```
val gradientBrush = Brush.verticalGradient(
. . .
    modifier.background(brush = gradientBrush)
```

ZStack == Box
```
    Box(
        Modifier
        .fillMaxSize()
        .background(Color.Transparent),
        contentAlignment = Alignment.BottomStart
    ) {
    Column(
    . . .
    Image...
```

No necesitas card para cornerradious

.clip(RoundedCornerShape(16.dp)),

### listas fijas
* interesant euso de row y repeat para tener elementos fijos con base a un arreeglo de items
