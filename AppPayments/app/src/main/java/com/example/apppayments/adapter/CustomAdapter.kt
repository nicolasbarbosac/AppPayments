import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppayments.R
import com.example.apppayments.model.AuthorizationResponse

class CustomAdapter (private val mList: ArrayList<AuthorizationResponse>, val clickListener: (AuthorizationResponse) -> Unit) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_authorization, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.textViewReceiptId.text = ItemsViewModel.receiptId
        holder.textViewRRN.text = ItemsViewModel.rrn
        holder.textViewStatusCode.text = ItemsViewModel.statusCode
        holder.textViewStatusDescription.text = ItemsViewModel.statusDescription
        (holder as ViewHolder).bind(mList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

  class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewReceiptId: TextView
        val textViewRRN: TextView
        val textViewStatusCode: TextView
        val textViewStatusDescription: TextView


      init {
          // Define click listener for the ViewHolder's View.
          textViewReceiptId = itemView.findViewById(R.id.textViewReceiptId)
          textViewRRN = itemView.findViewById(R.id.textViewRRN)
          textViewStatusCode = itemView.findViewById(R.id.textViewStatusCode)
          textViewStatusDescription = itemView.findViewById(R.id.textViewStatusDescription)
      }
      fun bind(part: AuthorizationResponse, clickListener: (AuthorizationResponse) -> Unit){
          itemView.setOnClickListener { clickListener(part)}
      }

  }

}