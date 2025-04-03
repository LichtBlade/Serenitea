import com.google.gson.annotations.SerializedName

data class BestOffer(
    @SerializedName("id")
    val id: Int,

    @SerializedName("category")
    val categoryName: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: String,  // It will come as a String, so you can convert it to Double later if needed

    @SerializedName("image_path")
    val imagePath: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("small_price")
    val smallPrice: String?,  // Nullable String to handle null values

    @SerializedName("medium_price")
    val mediumPrice: String?,  // Nullable String to handle null values

    @SerializedName("large_price")
    val largePrice: String?  // Nullable String to handle null values
) {
    // Optional: Convert price fields to Double if needed
    fun getSmallPriceAsDouble(): Double? {
        return smallPrice?.toDoubleOrNull()
    }

    fun getMediumPriceAsDouble(): Double? {
        return mediumPrice?.toDoubleOrNull()
    }

    fun getLargePriceAsDouble(): Double? {
        return largePrice?.toDoubleOrNull()
    }

    fun getPriceAsDouble(): Double {
        return price.toDoubleOrNull() ?: 0.0
    }
}
