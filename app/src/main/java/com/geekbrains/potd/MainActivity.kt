package com.geekbrains.potd

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.geekbrains.potd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        mainViewModel.themeId?.let { setTheme(it) }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topToolbar.setOnMenuItemClickListener { onOptionsItemSelected(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuThemeSteel -> setThemeRecreate(R.style.Theme_Base_BlueGray)
            R.id.menuThemeCopper -> setThemeRecreate(R.style.Theme_Base_OrangeGreen)
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setThemeRecreate(themeId: Int): Boolean {
        mainViewModel.themeId = themeId
        recreate()
        return true
    }
}