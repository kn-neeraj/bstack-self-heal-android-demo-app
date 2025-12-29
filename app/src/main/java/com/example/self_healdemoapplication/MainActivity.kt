package com.example.self_healdemoapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.self_healdemoapplication.databinding.ActivityMainBinding
import com.example.self_healdemoapplication.ui.fragments.LoginFragment
import com.example.self_healdemoapplication.viewmodel.SelfHealViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SelfHealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SelfHealViewModel::class.java]

        setupToggleBar()

        // Show login fragment initially
        if (savedInstanceState == null) {
            replaceFragment(LoginFragment())
        }
    }

    private fun setupToggleBar() {
        val demoModeSwitch: SwitchCompat = binding.demoModeSwitch
        val demoModeStatus: TextView = binding.demoModeStatus

        demoModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleDemoMode()
            demoModeStatus.text = if (isChecked) "Toggle Mode: Active" else "Toggle Mode: Inactive"
        }

        viewModel.isDemoModeEnabled.observe(this) { isEnabled ->
            demoModeSwitch.isChecked = isEnabled
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun getViewModel(): SelfHealViewModel = viewModel
}
