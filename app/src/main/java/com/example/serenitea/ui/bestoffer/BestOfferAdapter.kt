import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.serenitea.R
import com.example.serenitea.ui.AddCard

class BestOfferAdapter(private val bestOffers: List<BestOffer>) :
    RecyclerView.Adapter<BestOfferAdapter.BestOfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestOfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bestoffercard, parent, false)
        return BestOfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestOfferViewHolder, position: Int) {
        val offer = bestOffers[position]
        holder.offerName.text = offer.name
        holder.priceButton.text = offer.largePrice


        // Dynamically assign images based on the offer name
        val imageResId = when (offer.name.toLowerCase()) {
            "americano" -> R.drawable.americano
            "matcha" -> R.drawable.matcha
            "espresso" -> R.drawable.espresso
            "latte" -> R.drawable.americano
            "cookies and cream" -> R.drawable.cookies_and_cream_
            "dark chocolate" -> R.drawable.dark_chocolate
            "ube" -> R.drawable.ube
            "wintermelon" -> R.drawable.wintermelon
            "chocolate" -> R.drawable.chocolate
            "okinawa" -> R.drawable.okinawa
            "honeydew" -> R.drawable.honeydew
            "mango" -> R.drawable.mango
            "milk coffee" -> R.drawable.milk_coffee
            "mocha coffee" -> R.drawable.mocha_cookie

            else -> R.drawable.americano  // Default placeholder image if no match
        }

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(imageResId)
            .into(holder.offerImage)

        // Set click listener to navigate to AddCardActivity
        holder.priceButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AddCard::class.java).apply {
                putExtra("name", offer.name)
                putExtra("image", imageResId)  // Pass the image resource ID
                putExtra("description", offer.description)

                putExtra("largePrice", offer.largePrice)
                putExtra("MediumPrice", offer.mediumPrice)
                putExtra("smallPrice", offer.smallPrice)

                putExtra("item_id", offer.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = bestOffers.size

    inner class BestOfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offerName: TextView = itemView.findViewById(R.id.homematcha_cafe)  // The name of the offer
        val offerImage: ImageView = itemView.findViewById(R.id.homeimgematcha)  // The image of the offer
        val priceButton: TextView = itemView.findViewById(R.id.price_button)  // The price button
        // Add to cart button
    }
}
