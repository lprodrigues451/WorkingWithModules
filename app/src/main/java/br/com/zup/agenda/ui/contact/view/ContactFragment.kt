package br.com.zup.agenda.ui.contact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.agenda.R
import com.example.network.data.model.UserResult
import br.com.zup.agenda.databinding.FragmentContactBinding
import br.com.zup.agenda.ui.contact.view.adapter.ContactAdapter
import br.com.zup.agenda.ui.contact.viewmodel.ContactViewModel
import br.com.zup.agenda.viewstate.ViewState

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding

    private val viewModel: ContactViewModel by lazy {
        ViewModelProvider(this)[ContactViewModel::class.java]
    }
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(arrayListOf(), this::clickList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContactBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listContact()
        observe()
        viewModel.getAllUser()
        binding.floatingActionButton.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_contactFragment_to_registrationFragment)
        }
    }

    private fun listContact() {
        binding.RvListContact.adapter = contactAdapter
        binding.RvListContact.layoutManager = LinearLayoutManager(context)
    }

    private fun clickList(contact: UserResult) {
        val bundle = bundleOf("CONTACT" to contact)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_contactFragment_to_detailFragment, bundle)
    }

//    private fun receptData() {
//        val contact = arguments?.getParcelableArrayList<UserResult>("CONTACT")
//        if (contact != null) {
//            contactAdapter.updateList(contact)
//        }
//    }

    private fun observe() {
        viewModel.useListState.observe(this.viewLifecycleOwner) {
            when (it) {
                is ViewState.Success -> {
                    contactAdapter.updateList(it.data)
                }
                is ViewState.Error -> {
                    Toast.makeText(
                        context,
                        "${it.throwable.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }
}
