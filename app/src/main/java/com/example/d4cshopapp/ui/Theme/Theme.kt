package com.example.d4cshop.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun D4CShopTheme(content: @Composable () -> Unit) {
    // Using a basic light color scheme (you can customize as needed)
    val colorScheme = lightColorScheme()
    MaterialTheme(colorScheme = colorScheme, content = content)

} 