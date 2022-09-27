package br.com.zup.agenda.ui.contact.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.network.data.model.UserResult
import br.com.zup.agenda.databinding.ContactItemBinding

class ContactAdapter(
    private var contactList: MutableList<UserResult>,
    private val clickList: (userResult: UserResult) -> Unit
) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    class ViewHolder(val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun showListContact(contactResult: UserResult) {
            binding.tvName.text = contactResult.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = contactList[position]
        holder.showListContact(user)
        holder.binding.CvItem.setOnClickListener {
            clickList(user)
        }

    }

    override fun getItemCount() = contactList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<UserResult>) {
        contactList = newList as MutableList<UserResult>
        notifyDataSetChanged()
    }


}
