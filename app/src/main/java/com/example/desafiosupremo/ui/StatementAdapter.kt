import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiosupremo.data.models.Statement
import com.example.desafiosupremo.data.models.TransferType
import com.example.desafiosupremo.databinding.StatementItemBinding
import com.example.desafiosupremo.utils.dateFormater
import com.example.desafiosupremo.utils.show

class StatementAdapter(
    private val onClick: (Statement) -> Unit
) : RecyclerView.Adapter<StatementAdapter.StatementViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Statement>() {
        override fun areItemsTheSame(oldItem: Statement, newItem: Statement): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Statement, newItem: Statement): Boolean {
            return oldItem.id == newItem.id
                && oldItem.name == newItem.name
                && oldItem.description == newItem.description
                && oldItem.createdAt == newItem.createdAt
                && oldItem.amount == newItem.amount
                && oldItem.transferType == newItem.transferType
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var items: List<Statement>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder {
        val binding =
            StatementItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return StatementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onClick)
    }

    override fun getItemCount() = items.size

    class StatementViewHolder(private val binding: StatementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Statement,
            onClick: (Statement) -> Unit
        ) = with(binding) {
            tvDescription.text = item.description
            tvName.text = item.name
            tvData.text = dateFormater(item.createdAt, "dd/MM")
            tvValue.text = "R$ ${item.amount},00"

            statementItem.setOnClickListener {
                onClick(item)
            }

            if (
                item.transferType == TransferType.PIXCASHOUT ||
                item.transferType == TransferType.PIXCASHIN
            ) tvSetPix.show()


            if (
                item.transferType == TransferType.TRANSFEROUT ||
                item.transferType == TransferType.PIXCASHOUT
            ) tvValue.text = "- RS$ ${item.amount},00"
        }
    }


}
