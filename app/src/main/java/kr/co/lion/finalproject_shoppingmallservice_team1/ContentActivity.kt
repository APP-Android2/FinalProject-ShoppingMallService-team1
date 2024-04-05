package kr.co.lion.finalproject_shoppingmallservice_team1

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityContentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCommentBinding

class ContentActivity : AppCompatActivity() {
    lateinit var activityContentBinding: ActivityContentBinding
    lateinit var navigationActivity: NavigationActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContentBinding = ActivityContentBinding.inflate(layoutInflater)

        settingToolbar()
        settingRecyclerComment()

        setContentView(activityContentBinding.root)
    }
    fun settingToolbar() {
        activityContentBinding.apply {
            toolbarContent.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    finish()
                }

                inflateMenu(R.menu.menu_community_content)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemContentEdit -> {
                            val intent = Intent(this@ContentActivity, CommunityEditActivity::class.java)
                            startActivity(intent)

                        }
                        R.id.menuItemContentChat -> {
                            val intent = Intent(this@ContentActivity, ChattingActivity::class.java)
                            startActivity(intent)
                        }

                        R.id.menuItemContentDeclaration -> {
                            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
                            materialAlertDialogBuilder.setTitle("신고")
                            materialAlertDialogBuilder.setMessage("이 게시글을 신고하시겠습니까?")
                            // 확인 버튼 누르면  다시 CommunityFragment로 돌아감
                            materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                // 스낵바 띄우기
                                Snackbar.make(this, "신고를 완료했습니다.", Snackbar.LENGTH_SHORT).show()
                            }
                            materialAlertDialogBuilder.show()
                        }

                        R.id.menuItemContentDel -> {
                            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
                            materialAlertDialogBuilder.setTitle("삭제")
                            materialAlertDialogBuilder.setMessage("이 게시글을 삭제하시겠습니까?")
                            // 확인 버튼 누르면  다시 CommunityFragment로 돌아감
                            materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                finish()
                            }
                            materialAlertDialogBuilder.show()
                        }
                    }
                    true
                }
            }
        }
    }

    fun settingRecyclerComment(){
        activityContentBinding.apply {
            recyclerViewContent.apply {
                adapter = CommentRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@ContentActivity)

                val deco = MaterialDividerItemDecoration(this@ContentActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    inner class CommentRecyclerViewAdapter:RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentViewHolder>(){
        inner class CommentViewHolder(rowCommentBinding: RowCommentBinding):RecyclerView.ViewHolder(rowCommentBinding.root){
            val rowCommentBinding:RowCommentBinding

            init {
                this.rowCommentBinding = rowCommentBinding
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
            val rowCommentBinding = RowCommentBinding.inflate(layoutInflater)
            val commentViewHolder = CommentViewHolder(rowCommentBinding)
            return commentViewHolder
        }

        override fun getItemCount(): Int {
            return 4
        }

        override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
            holder.rowCommentBinding.textViewCommentNickname.text = "홍길동"
            holder.rowCommentBinding.textViewComment.text = "댓글 내용----------------"
            holder.rowCommentBinding.textViewCommentDate2.text = "04/05 14:42"
        }
    }
}