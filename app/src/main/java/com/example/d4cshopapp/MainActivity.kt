package com.example.d4cshopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.d4cshop.ui.theme.D4CShopTheme
import com.example.d4cshopapp.ui.screens.ShopScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            D4CShopTheme {
                ShopScreen()
            }
        }
    }
}
