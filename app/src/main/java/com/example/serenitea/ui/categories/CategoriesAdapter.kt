import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serenitea.R

class CategoriesAdapter(private val categoriesList: List<Category>) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categoriescard, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoriesList[position]
        holder.categoryName.text = category.categoryName

        // Dynamically assign images based on the category name
        val imageResId = when (category.categoryName.toLowerCase()) {
            "beverages" -> R.drawable.matcha
            "food" -> R.drawable.cheese_burger
            else -> R.drawable.americano  // Default placeholder image if no match
        }

        // Set the image resource directly
        holder.categoryImage.setImageResource(imageResId)
    }

    override fun getItemCount(): Int = categoriesList.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.category_name)
        val categoryImage: ImageView = itemView.findViewById(R.id.category_image)
    }
}
