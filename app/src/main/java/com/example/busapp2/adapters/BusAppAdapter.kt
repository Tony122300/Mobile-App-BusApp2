import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busapp2.databinding.CardBusappBinding
import com.example.busapp2.models.BusAppModel
import com.squareup.picasso.Picasso


interface BusAppListener {
    fun onBusAppClick(buses: BusAppModel, position: Int){

    }
}

class BusAppAdapter constructor(private var buses: List<BusAppModel>,private val listener: BusAppListener) :
    RecyclerView.Adapter<BusAppAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBusappBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }



    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val busApp = buses[holder.adapterPosition]
        holder.bind(busApp, listener)
    }

    override fun getItemCount(): Int = buses.size


    class MainHolder(private val binding: CardBusappBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(busApp: BusAppModel, listener: BusAppListener) {
            binding.origin.text = busApp.origin
            binding.destination.text = busApp.destination
            Picasso.get().load(busApp.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener{listener.onBusAppClick(busApp,adapterPosition)}
        }
    }

}
