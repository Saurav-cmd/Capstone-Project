import com.saurav.boozebuddy.models.Item
import com.saurav.boozebuddy.screens.home.items

fun getItemById(itemId: Int?): Item? {
    return items.find { it.id == itemId }
}