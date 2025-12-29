package com.example.self_healdemoapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.self_healdemoapplication.MainActivity
import com.example.self_healdemoapplication.R
import com.example.self_healdemoapplication.data.DemoUsers
import com.example.self_healdemoapplication.databinding.FragmentLoginBinding
import com.example.self_healdemoapplication.viewmodel.HealingElement
import com.example.self_healdemoapplication.viewmodel.SelfHealViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SelfHealViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).getViewModel()

        setupUserDropdown()
        setupDemoModeObserver()
        setupSignInButton()
    }

    private fun setupUserDropdown() {
        val userEmails = DemoUsers.users.map { it.email }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            userEmails
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.userDropdown.adapter = adapter

        binding.userDropdown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val user = DemoUsers.users[position]
                    binding.emailInput.setText(user.email)
                    binding.passwordInput.setText(user.password)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setupDemoModeObserver() {
        viewModel.isDemoModeEnabled.observe(viewLifecycleOwner) { isDemoMode ->
            updateElementIds(isDemoMode)
            updateHealingNotifications(isDemoMode)
            binding.healingBanner.visibility = if (isDemoMode) View.VISIBLE else View.GONE
        }

        viewModel.healingElement.observe(viewLifecycleOwner) { healingElement ->
            updateHealingNotifications(viewModel.isDemoMode(), healingElement)
        }
    }

    private fun updateElementIds(isDemoMode: Boolean) {
        // Update IDs for Appium to find
        // Dropdown ID remains unchanged to keep it stable for Appium tests

        binding.emailInput.id = if (isDemoMode) {
            View.generateViewId()
        } else {
            R.id.email_input
        }
        ViewCompat.setAccessibilityPaneTitle(
            binding.emailInput,
            if (isDemoMode) "email_input_modified" else "email_input"
        )

        binding.passwordInput.id = if (isDemoMode) {
            View.generateViewId()
        } else {
            R.id.password_input
        }
        ViewCompat.setAccessibilityPaneTitle(
            binding.passwordInput,
            if (isDemoMode) "password_input_modified" else "password_input"
        )

        binding.signInButton.id = if (isDemoMode) {
            View.generateViewId()
        } else {
            R.id.sign_in_button
        }
        ViewCompat.setAccessibilityPaneTitle(
            binding.signInButton,
            if (isDemoMode) "sign_in_button_modified" else "sign_in_button"
        )
    }

    private fun updateHealingNotifications(
        isDemoMode: Boolean,
        healingElement: HealingElement? = null
    ) {
        val element = healingElement ?: viewModel.healingElement.value ?: HealingElement.SELECT_USER

        binding.dropdownHealingNotification.visibility =
            if (isDemoMode && (element == HealingElement.SELECT_USER || element == HealingElement.ALL))
                View.VISIBLE else View.GONE

        binding.emailHealingNotification.visibility =
            if (isDemoMode && (element == HealingElement.EMAIL || element == HealingElement.ALL))
                View.VISIBLE else View.GONE

        binding.passwordHealingNotification.visibility =
            if (isDemoMode && (element == HealingElement.PASSWORD || element == HealingElement.ALL))
                View.VISIBLE else View.GONE

        binding.signInHealingNotification.visibility =
            if (isDemoMode && (element == HealingElement.SIGN_IN || element == HealingElement.ALL))
                View.VISIBLE else View.GONE
    }

    private fun setupSignInButton() {
        binding.signInButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            val user = DemoUsers.getUserByEmail(email)
            if (user != null && user.password == password) {
                (activity as MainActivity).replaceFragment(ProductsFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
