import android.annotation.SuppressLint
import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiosupremo.R
import com.example.desafiosupremo.databinding.StatementItemBinding
import com.example.desafiosupremo.models.Statement
import com.example.desafiosupremo.models.TransferType
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class StatementAdapter(private val items: List<Statement>, private val onClick: OnClick) :
    RecyclerView.Adapter<StatementAdapter.StatementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder {
        val binding =
            StatementItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            onClick.onClickListener(item)
        }
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    interface OnClick {
        fun onClickListener(statement: Statement)
    }

    class StatementViewHolder(private val binding: StatementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Statement) = with(binding) {
            tvDescription.text = item.description
            tvName.text = item.to
            tvData.text = dateFormat(item.createdAt)
            tvValue.text = "R$ ${item.amount},00"

            if (item.tType == TransferType.PIXCASHOUT || item.tType == TransferType.PIXCASHIN) {
                tvSetPix.visibility = View.VISIBLE
            }


            if (item.tType == TransferType.TRANSFEROUT || item.tType == TransferType.PIXCASHIN)
                tvValue.text = "- RS$ ${item.amount},00"
        }

        private fun dateFormat(createdAt: String): String {
            val dateReciver = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
            val date = dateReciver.parse(createdAt)
            val originalDate = SimpleDateFormat("dd/MM")
            return originalDate.format(date)
        }
    }
}
