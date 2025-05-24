package com.example.d4cshopapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.d4cshopapp.R
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.shadow

// --- Data Models ---
data class Product(
    val name: String,
    val description: String,
    val highlight: String,
    val price: Double,
    val oldPrice: Double?,
    val discount: Int?,
    val rating: Double,
    val reviews: Int,
    val inStock: Boolean,
    val bestSeller: Boolean = false,
    val imageRes: Int
)

data class Category(val name: String, val iconRes: Int)

// --- Sample Data ---
val categories = listOf(
    Category("Cleaners", R.drawable.categorysample),
    Category("Toner", R.drawable.categorysample),
    Category("Serums", R.drawable.categorysample),
    Category("Moisturisers", R.drawable.categorysample),
    Category("Sunscreen", R.drawable.categorysample)
)

val productList = listOf(
    Product(
        name = "clencera",
        description = "French clay and algae-powered cleanser",
        highlight = "Skin Tightness • Dry & Dehydrated Skin",
        price = 355.20,
        oldPrice = 444.00,
        discount = 20,
        rating = 5.0,
        reviews = 249,
        inStock = true,
        bestSeller = true,
        imageRes = R.drawable.productimage
    ),
    Product(
        name = "glow",
        description = "French clay and algae-powered cleanser",
        highlight = "Skin Tightness • Dry & Dehydrated Skin",
        price = 355.20,
        oldPrice = 444.00,
        discount = 20,
        rating = 5.0,
        reviews = 249,
        inStock = true,
        bestSeller = false,
        imageRes = R.drawable.productimage
    ),
    Product(
        name = "afterglow",
        description = "French clay and algae-powered cleanser",
        highlight = "Skin Tightness • Dry & Dehydrated Skin",
        price = 355.20,
        oldPrice = 444.00,
        discount = 20,
        rating = 5.0,
        reviews = 249,
        inStock = false,
        bestSeller = false,
        imageRes = R.drawable.productimage
    )
)

// --- UI ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen() {
    // State for liked and cart products
    val likedProducts = remember { mutableStateOf(setOf<String>()) }
    val cartProducts = remember { mutableStateOf(setOf<String>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF232323), Color(0xFF444444)),
                    startY = 0f, endY = 2000f
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                ShopTopBar(
                    likedCount = likedProducts.value.size,
                    cartCount = cartProducts.value.size
                )
            }
            item { PromoBanner() }
            item { CategoriesSection() }
            item { SectionHeader(title = "New products") }
            items(productList) { product ->
                ProductCard(
                    product = product,
                    isLiked = likedProducts.value.contains(product.name),
                    isInCart = cartProducts.value.contains(product.name),
                    onLikeClick = {
                        likedProducts.value = if (likedProducts.value.contains(product.name)) {
                            likedProducts.value - product.name
                        } else {
                            likedProducts.value + product.name
                        }
                    },
                    onAddToCartClick = {
                        if (!cartProducts.value.contains(product.name)) {
                            cartProducts.value = cartProducts.value + product.name
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ShopTopBar(likedCount: Int, cartCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* TODO: Back */ }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
        Text(
            text = "Shop",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        IconWithBadge(Icons.Default.Favorite, badgeCount = likedCount)
        Spacer(Modifier.width(8.dp))
        IconWithBadge(Icons.Default.ShoppingCart, badgeCount = cartCount)
    }
}

@Composable
fun IconWithBadge(icon: androidx.compose.ui.graphics.vector.ImageVector, badgeCount: Int) {
    Box {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 6.dp, y = (-4).dp)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFB6FF5B)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeCount.toString(),
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PromoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(140.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.promo_banner),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, top = 20.dp, end = 32.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "GET 20% OFF",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Get 20% off",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFB6FF5B))
                            .padding(horizontal = 12.dp, vertical = 2.dp)
                    ) {
                        Text(
                            "12-16 October",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IndicatorDot(selected = true)
                Spacer(Modifier.width(6.dp))
                IndicatorDot(selected = false)
                Spacer(Modifier.width(6.dp))
                IndicatorDot(selected = false)
            }
        }
    }
}

@Composable
fun IndicatorDot(selected: Boolean) {
    Box(
        modifier = Modifier
            .size(if (selected) 16.dp else 8.dp)
            .clip(CircleShape)
            .background(if (selected) Color(0xFFB6FF5B) else Color.White.copy(alpha = 0.3f))
    )
}

@Composable
fun CategoriesSection() {
    Column(Modifier.padding(top = 8.dp)) {
        SectionHeader(title = "Categories")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(categories) { category ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF232323)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = category.iconRes),
                            contentDescription = category.name,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(category.name, color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Text("See all", color = Color(0xFFB6FF5B), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun ProductCard(
    product: Product,
    isLiked: Boolean,
    isInCart: Boolean,
    onLikeClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Product banner background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_banner),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            // Product image centered within the banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(180.dp)
                        .shadow(8.dp, RoundedCornerShape(18.dp)),
                    contentScale = ContentScale.Fit
                )
            }
            // Heart icon (slightly outside top left)
            Box(
                modifier = Modifier
                    .offset(x = (-18).dp, y = (-18).dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF232323))
                    .clickable { onLikeClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (isLiked) Color(0xFFB6FF5B) else Color(0xFFB6FF5B).copy(alpha = 0.5f),
                    modifier = Modifier.size(24.dp)
                )
            }
            // Best seller badge (slightly outside top right)
            if (product.bestSeller) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 18.dp, y = (-18).dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFB6FF5B))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("Best seller", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
        // Info panel with custom background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .offset(y = (-20).dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_info_banner),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        product.name,
                        color = Color(0xFFB6FF5B),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(8.dp))
                    if (product.inStock) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFB6FF5B))
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("In stock", color = Color(0xFFB6FF5B), fontSize = 14.sp)
                    } else {
                        Text("Out of stock", color = Color.Red, fontSize = 14.sp)
                    }
                }
                Text(
                    product.description,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    product.highlight,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "RS. %.2f".format(product.price),
                        color = Color(0xFF7B61FF),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    if (product.oldPrice != null) {
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "RS. %.2f".format(product.oldPrice),
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with star icon
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "${product.reviews} reviews",
                        color = Color.White,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(Modifier.weight(1f))
                
                // Add to cart button (aligned at bottom of info section)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .offset(x = 12.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(
                        onClick = onAddToCartClick,
                        enabled = product.inStock && !isInCart,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(if (product.inStock && !isInCart) Color(0xFFB6FF5B) else Color.Gray.copy(alpha = 0.3f))
                            .align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = if (product.inStock && !isInCart) Color.Black else Color.White.copy(alpha = 0.5f),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeBox(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}
