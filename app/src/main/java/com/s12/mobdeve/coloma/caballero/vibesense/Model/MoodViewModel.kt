import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.s12.mobdeve.coloma.caballero.vibesense.Model.Mood
import com.s12.mobdeve.coloma.caballero.vibesense.Model.User
import com.s12.mobdeve.coloma.caballero.vibesense.Repository.MoodRepository


class MoodViewModel: ViewModel() {

    private val repository: MoodRepository
    private val _allMoods = MutableLiveData<List<Mood>>()
    val allMoods : LiveData<List<Mood>> = _allMoods
    private val mAuth = FirebaseAuth.getInstance()
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://vibesense-9523f-default-rtdb.asia-southeast1.firebasedatabase.app")


    init {
        repository = MoodRepository().getInstance()
        val currentUser = mAuth.currentUser
        var email = currentUser?.email

        getUserIdFromEmail(email.toString()) { userId ->
            if (userId != null) {
                repository.loadMoodByUserId(userId, _allMoods)
            }
        }
    }



    private fun getUserIdFromEmail(email: String, callback: (String?) -> Unit) {
        val userReference = databaseReference.child("Users")
        userReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        val userId = userSnapshot.key
                        callback(userId)
                        return
                    }
                }
                callback(null)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

}
