package br.com.zup.agenda.ui.home.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.zup.agenda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val nav: NavController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, nav)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navHostFragment.navController.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}