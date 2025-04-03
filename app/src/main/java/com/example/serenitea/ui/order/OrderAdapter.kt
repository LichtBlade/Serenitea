package com.example.serenitea.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.serenitea.R
import com.example.serenitea.data.model.Order

class OrderAdapter(
    private val context: Context,
    private val orders: List<Order>
) : BaseAdapter() {

    override fun getCount(): Int {
        return orders.size
    }

    override fun getItem(position: Int): Any {
        return orders[position]
    }

    override fun getItemId(position: Int): Long {
        return orders[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)

        // Find the TextViews
        val nameTextView: TextView = view.findViewById(R.id.Name)
        val quantityTextView: TextView = view.findViewById(R.id.orderQuantity)
        val totalPriceTextView: TextView = view.findViewById(R.id.orderTotalPrice)

        // Get the current order data
        val order = orders[position]

        // Set the data into the TextViews
        nameTextView.text = order.item_name // "latte"
        quantityTextView.text = "Quantity: ${order.quantity}" // "Quantity: 2"
        totalPriceTextView.text = "PHP ${"%.2f".format(order.total_price)}" // "PHP 40.00"

        return view
    }
}
