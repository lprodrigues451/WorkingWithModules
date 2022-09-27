package br.com.zup.agenda.ui.registration.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.network.data.model.UserResult
import br.com.zup.agenda.databinding.FragmentRegistrationBinding
import br.com.zup.agenda.ui.registration.viewmodel.RegisterUserViewModel
import br.com.zup.agenda.viewstate.ViewState


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    private val viewModel: RegisterUserViewModel by lazy {
        ViewModelProvider(this)[RegisterUserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSearch.setOnClickListener {
            val cep = binding.etCepUm.text.toString()
            viewModel.getUser(cep)
            receptCep()
            initObserver()
            clickOnButtonAdd()
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun receptCep() {
        viewModel.userResponse.observe(viewLifecycleOwner, Observer {
            it.let {
                binding.etCepUm.text = it.cep.toEditable()
                binding.etCity.text = it.cidade.toEditable()
                binding.etDistrict.text = it.bairro.toEditable()
                binding.etRoad.text = it.logradouro.toEditable()
                binding.etState.text = it.estado.toEditable()
            }
        })
    }

    private fun clearFieldEdition() {
        binding.etName.text.clear()
        binding.etCity.text.clear()
        binding.etRoad.text.clear()
        binding.etDistrict.text.clear()
        binding.etCepUm.text.clear()
        binding.etNunber.text.clear()
        binding.etPhone.text.clear()
        binding.etState.text.clear()
    }

    private fun initObserver() {
        viewModel.userResultAddState.observe(this.viewLifecycleOwner) {
            when (it) {
                is ViewState.Success -> {
                    Toast.makeText(context, "Contato cadastrado com sucesso!", Toast.LENGTH_LONG)
                        .show()
                    clearFieldEdition()
                }
                is ViewState.Error -> {
                    Toast.makeText(context, "${it.throwable.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun clickOnButtonAdd() {
        binding.btnRegister.setOnClickListener {
            val user = UserResult(
                name = binding.etName.text.toString(),
                phone = binding.etPhone.text.toString(),
                cep = binding.etCepUm.text.toString(),
                cidade = binding.etCity.text.toString(),
                bairro = binding.etDistrict.text.toString(),
                number = binding.etNunber.text.toString(),
                estado = binding.etState.text.toString(),
                logradouro = binding.etRoad.text.toString()
            )
            viewModel.insertUser(user)
        }
    }
}

