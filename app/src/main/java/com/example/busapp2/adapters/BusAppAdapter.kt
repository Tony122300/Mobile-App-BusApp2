import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busapp2.databinding.CardBusappBinding
import com.example.busapp2.models.BusAppModel

class BusAppAdapter constructor(private var buses: List<BusAppModel>) :
    RecyclerView.Adapter<BusAppAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBusappBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }



    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val busApp = buses[holder.adapterPosition]
        holder.bind(busApp)
    }

    override fun getItemCount(): Int = buses.size


    class MainHolder(private val binding: CardBusappBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(buses: BusAppModel) {
            binding.origin.text = buses.origin
            binding.destination.text = buses.destination
        }
    }

}
