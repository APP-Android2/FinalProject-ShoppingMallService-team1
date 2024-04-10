package kr.co.lion.finalproject_shoppingmallservice_team1

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityContentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCommentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCommentReplyBinding

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
                            AppAlertDialog(this@ContentActivity, "신고", "이 게시글을 신고하시겠습니까?", "신고", "취소", 1).show(
                                onClickPositive = {
                                    Snackbar.make(this, "신고를 완료했습니다.", Snackbar.LENGTH_SHORT).show()
                                }
                            )
                        }

                        R.id.menuItemContentDel -> {
                            AppAlertDialog(this@ContentActivity, "삭제", "이 게시글을 삭제하시겠습니까?", "삭제", "취소", 1).show(
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

                rowCommentBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
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
            holder.rowCommentBinding.buttonCommentLike.setOnClickListener {
//                 val button = holder.rowCommentBinding.buttonCommentLike
//                // 클릭할 때마다 아이콘을 변경합니다
//                val drawable = if (button.compoundDrawablesRelative[2] == ContextCompat.getDrawable(this@ContentActivity, R.drawable.favorite_fill)) {
//                    ContextCompat.getDrawable(this@ContentActivity, R.drawable.favorite)
//
//                } else {
//                    ContextCompat.getDrawable(this@ContentActivity, R.drawable.favorite_fill)
//                }
//                button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
            }

            holder.rowCommentBinding.buttonCommentComment.setOnClickListener {
                AppAlertDialog(this@ContentActivity, "대댓글", "대댓글을 작성하시겠습니까?", "작성", "취소").show(
                    onClickPositive = {
                        Tools.showSoftInput(this@ContentActivity, activityContentBinding.editTextComment)
                        // https://lakue.tistory.com/15 참고해서 대댓글 구현
                    }
                )
            }
            holder.rowCommentBinding.buttonCommentMenu.setOnClickListener {
                val menuResId = R.menu.menu_comment

                val contextWrapper = ContextThemeWrapper(this@ContentActivity, R.style.popupMenuStyle)

                val popupMenu = PopupMenu(contextWrapper, it)

                popupMenu.inflate(menuResId)

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menuItemCommentChat -> {
                            val intent = Intent(this@ContentActivity, ChattingActivity::class.java)
                            startActivity(intent)
                        }

                        R.id.menuItemCommentDeclaration -> {
                            AppAlertDialog(this@ContentActivity, "신고", "이 게시글을 신고하시겠습니까?", "신고", "취소", 1).show(
                                onClickPositive = {
                                    Snackbar.make(it, "신고를 완료했습니다.", Snackbar.LENGTH_SHORT).show()
                                }
                            )
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                    true
                }
                popupMenu.show()
            }
        }
    }
}