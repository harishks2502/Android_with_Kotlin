package com.example.milestone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.milestone.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val mailId: String = binding.loginEmailIDField.text.toString().trim()
            val password: String = binding.loginPasswordField.text.toString().trim()

            if (mailId.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Both fields are required", Toast.LENGTH_SHORT).show()
            } else if (mailId == "harish@gmail.com" && password == "admin") {
                Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment2_to_dashboardFragment2)
            } else {
                Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}