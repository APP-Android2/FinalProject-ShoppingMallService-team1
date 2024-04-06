package kr.co.lion.finalproject_shoppingmallservice_team1

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.PopupMenu
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
                            AppAlertDialog(this@ContentActivity, "신고", "이 게시글을 신고하시겠습니까?").show(
                                onClickPositive = {
                                    Snackbar.make(this, "신고를 완료했습니다.", Snackbar.LENGTH_SHORT).show()
                                }
                            )
                        }

                        R.id.menuItemContentDel -> {
                            AppAlertDialog(this@ContentActivity, "삭제", "이 게시글을 삭제하시겠습니까?").show(
                                onClickPositive = {
                                    finish()
                                }
                            )
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
            holder.rowCommentBinding.buttonCommentMenu.setOnClickListener {
                // menu_comment의 리소스 ID를 가져옵니다.
                val menuResId = R.menu.menu_comment

                // 팝업 메뉴를 생성하고 RecyclerView의 버튼을 클릭한 위치에 표시합니다.
                val popupMenu = PopupMenu(this@ContentActivity, holder.rowCommentBinding.buttonCommentComment)

                // 팝업 메뉴에 menu_comment의 아이템을 추가합니다.
                popupMenu.inflate(menuResId)

                // 팝업 메뉴의 아이템을 클릭했을 때의 동작을 정의합니다.
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    // menuItem의 ID에 따라 필요한 동작을 수행합니다.
                    when (menuItem.itemId) {
                        R.id.menuItemCommentChat -> {
                            val intent = Intent(this@ContentActivity, ChattingActivity::class.java)
                            startActivity(intent)
                        }

                        R.id.menuItemCommentDeclaration -> {
                            AppAlertDialog(this@ContentActivity, "신고", "이 게시글을 신고하시겠습니까?").show(
                                onClickPositive = {
                                    Snackbar.make(holder.rowCommentBinding.buttonCommentComment, "신고를 완료했습니다.", Snackbar.LENGTH_SHORT).show()
                                }
                            )
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                    true
                }

                // 팝업 메뉴를 표시합니다.
                popupMenu.show()
            }
        }
    }
}