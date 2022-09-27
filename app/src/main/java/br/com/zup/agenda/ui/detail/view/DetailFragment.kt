package br.com.zup.agenda.ui.detail.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import br.com.zup.agenda.R
import com.example.network.data.model.UserResult
import br.com.zup.agenda.databinding.FragmentDetailBinding
import br.com.zup.agenda.ui.detail.viewmodel.DetailViewModel
import br.com.zup.teste.Exemplo
import br.com.zup.teste.TestActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrieveViewUserData()
    }

    private fun retrieveViewUserData() {
        val user = arguments?.getParcelable<UserResult>("CONTACT")
        if (user != null) {
            displayInformation(user)
            clickNoButtonDelete(user)
            clickOnButtonEdit(user)
            clickNoButtonTeste()

        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayInformation(user: UserResult) {
        val exemplo = Exemplo()
        binding.tvName.text = exemplo.name + user.name
        binding.tvPhone.text = exemplo.phone + user.phone
        binding.tvDistrict.text = exemplo.district + user.bairro
        binding.tvRoad.text = exemplo.road + user.logradouro
        binding.tvNunber.text = exemplo.nunber + user.number
        binding.tvState.text = exemplo.state + user.estado
        binding.tvCity.text = exemplo.city + user.cidade

    }

    private fun clickOnButtonEdit(user: UserResult) {
        binding.btnEdit.setOnClickListener {
            val bundle = bundleOf("USER" to user)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_detailFragment_to_editUserFragment, bundle)
        }
    }

    private fun clickNoButtonDelete(user: UserResult) {
        binding.btnDelete.setOnClickListener {
            showConfirmationDialog(user)

        }
    }

    private fun clickNoButtonTeste() {
        binding.btnTest.setOnClickListener {
            startActivity(Intent(context, TestActivity::class.java))
        }
    }

    private fun showConfirmationDialog(user: UserResult) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteUser(user)
            }
            .show()
    }

    private fun deleteUser(user: UserResult) {
        viewModel.deleteAllList(user)
        findNavController().navigateUp()
    }
}