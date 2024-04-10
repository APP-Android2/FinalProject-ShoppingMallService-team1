package kr.co.lion.finalproject_shoppingmallservice_team1

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityChatBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowHomeChatBinding

class ChatActivity : AppCompatActivity() {
    lateinit var activityChatBinding: ActivityChatBinding
    lateinit var navigationActivity: NavigationActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityChatBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(activityChatBinding.root)

        settingToolbar()
        settingRecyclerViewChat()
    }

    fun settingToolbar() {
        activityChatBinding.apply {
            toolbarHomeChat.apply {
                setNavigationIcon(R.drawable.close)

                setNavigationOnClickListener {
                    finish()
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    // 쪽지 목록
    fun settingRecyclerViewChat(){
        activityChatBinding.apply {
            recyclerViewHomeChat.apply {
                adapter = ChatRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@ChatActivity)

                val deco = MaterialDividerItemDecoration(this@ChatActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    inner class ChatRecyclerViewAdapter: RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder>(){
        inner class ChatViewHolder(rowHomeChatBinding: RowHomeChatBinding): RecyclerView.ViewHolder(rowHomeChatBinding.root){
            val rowHomeChatBinding: RowHomeChatBinding

            init {
                this.rowHomeChatBinding = rowHomeChatBinding
                rowHomeChatBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
            val rowHomeChatBinding = RowHomeChatBinding.inflate(layoutInflater)
            val chatViewHolder = ChatViewHolder(rowHomeChatBinding)
            return chatViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
            holder.rowHomeChatBinding.textViewHomeChatName.text = "홍길동"
            holder.rowHomeChatBinding.textViewHomeChatContent.text = "헬스장 가격 문의 답장입니다."
            holder.rowHomeChatBinding.textViewHomeChatDate.text = "2024-03-29"

            // 쪽지 목록 클릭 시 채팅 액티비티로 넘어감
            holder.rowHomeChatBinding.root.setOnClickListener {
                // 필요한 데이터를 담아준다.
                //val readBundle = Bundle()
                //readBundle.putInt("contentIdx", mainList[position].contentIdx)
                val intent = Intent(this@ChatActivity, ChattingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}