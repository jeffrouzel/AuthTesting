package com.example.authtesting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.authtesting.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var  viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        binding.buttonRegister.setOnClickListener {
            val username = binding.inputUsernameRegister.text.toString()
            val password = binding.inputPasswordRegister.text.toString()
            val confirmPassword = binding.inputConfirmPasswordRegister.text.toString()

            when {
                username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
                password != confirmPassword -> {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.register(username, password)
                }
            }
        }

        binding.redirectLogin.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                UserViewModel.RegisterResult.SUCCESS -> {
                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
                UserViewModel.RegisterResult.USER_EXISTS -> {
                    Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}