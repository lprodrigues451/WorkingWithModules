package br.com.zup.agenda.ui.edituser.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.zup.agenda.R
import com.example.network.data.model.UserResult
import br.com.zup.agenda.databinding.FragmentEditUserBinding
import br.com.zup.agenda.ui.edituser.viewmodel.EditUseViewModel
import br.com.zup.agenda.viewstate.ViewState

class EditUserFragment : Fragment() {
    private lateinit var binding: FragmentEditUserBinding
    private val viewModel: EditUseViewModel by lazy {
        ViewModelProvider(this)[EditUseViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        retrieveViewUserData()
    }

    private fun retrieveViewUserData() {
        val user = arguments?.getParcelable<UserResult>("USER")
        if (user != null) {
            receptUser(user)
            clickOnButtonSave(user)

        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


    private fun receptUser(userResult: UserResult) {

        binding.etName.text = userResult.name.toEditable()
        binding.etCity.text = userResult.cidade.toEditable()
        binding.etRoad.text = userResult.logradouro.toEditable()
        binding.etDistrict.text = userResult.bairro.toEditable()
        binding.etCepUm.text = userResult.cep.toEditable()
        binding.etNunber.text = userResult.number.toEditable()
        binding.etPhone.text = userResult.phone.toEditable()
        binding.etState.text = userResult.estado.toEditable()

    }

    private fun initObserver() {
        viewModel.userResultAddState.observe(this.viewLifecycleOwner) {
            when (it) {
                is ViewState.Success -> {
                    Toast.makeText(context, "Contato editado com sucesso!", Toast.LENGTH_LONG)
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

    private fun clickOnButtonSave(userTwo: UserResult) {
        binding.btnSave.setOnClickListener {
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
            viewModel.updateItem(user)
            deleteUser(userTwo)

        }
    }

    private fun deleteUser(user: UserResult) {
        viewModel.deleteAllList(user)
        findNavController().navigate(R.id.action_editUserFragment_to_contactFragment)

    }
}