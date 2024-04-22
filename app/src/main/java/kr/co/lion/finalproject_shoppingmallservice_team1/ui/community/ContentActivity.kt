package kr.co.lion.finalproject_shoppingmallservice_team1.ui.community

import CommunityPost
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.Tools
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.CommunityDao
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityContentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCommentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.chat.ChattingActivity

class ContentActivity : AppCompatActivity() {
    lateinit var activityContentBinding: ActivityContentBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var communityPost: CommunityPost
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContentBinding = ActivityContentBinding.inflate(layoutInflater)

        val communityContentIdx = intent.getStringExtra("communityContent")
        if (communityContentIdx != null) {
            // 여기서 communityContent를 사용하여 원하는 작업을 수행합니다.
            settingContent(communityContentIdx.toInt())
        } else {
            // "communityContent" 키로 전달된 데이터가 없을 때 처리할 작업을 수행합니다.
        }


        settingToolbar()
        settingRecyclerComment()

        setContentView(activityContentBinding.root)
    }

    fun settingContent(communityContentIdx:Int){
        CoroutineScope(Dispatchers.Main).launch {
            val communityPost = CommunityDao.getCommnunityPost(communityContentIdx)
            activityContentBinding.apply {
                textViewContentTitle.text = communityPost.title
                textViewContentWrite.text = communityPost.content
            }
        }
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
                            MaterialAlertDialogBuilder(this@ContentActivity, R.style.MyDialogTheme).apply {
                                setTitle("게시글 신고")
                                setMessage("이 게시글을 신고하시겠습니까?")
                                setNegativeButton("취소", null)
                                setPositiveButton("신고"){ dialogInterface: DialogInterface, i: Int ->
                                    Snackbar.make(activityContentBinding.root, "신고를 완료했습니다.", Snackbar.LENGTH_SHORT).show()
                                }
                                show().apply {
                                    getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                                    getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
                                }
                            }
                        }

                        R.id.menuItemContentDel -> {
                            MaterialAlertDialogBuilder(this@ContentActivity, R.style.MyDialogTheme).apply {
                                setTitle("게시글 삭제")
                                setMessage("이 게시글을 삭제하시겠습니까?")
                                setNegativeButton("취소", null)
                                setPositiveButton("삭제"){ dialogInterface: DialogInterface, i: Int ->
                                    finish()
                                }
                                show().apply {
                                    getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                                    getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
                                }
                            }
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

    inner class CommentRecyclerViewAdapter:RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentViewHolder>() {
        inner class CommentViewHolder(rowCommentBinding: RowCommentBinding) :
            RecyclerView.ViewHolder(rowCommentBinding.root) {
            val rowCommentBinding: RowCommentBinding

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
                MaterialAlertDialogBuilder(this@ContentActivity, R.style.MyDialogTheme).apply {
                    setTitle("대댓글")
                    setMessage("대댓글을 작성하시겠습니까?")
                    setNegativeButton("취소", null)
                    setPositiveButton("작성") { dialogInterface: DialogInterface, i: Int ->
                        Tools.showSoftInput(
                            this@ContentActivity,
                            activityContentBinding.editTextComment
                        )
                        // https://lakue.tistory.com/15 참고해서 대댓글 구현
                    }
                    show().apply {
                        getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                        getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.Pup_Color
                            )
                        )
                    }
                }
            }
            holder.rowCommentBinding.buttonCommentMenu.setOnClickListener {
                val menuResId = R.menu.menu_comment

                val contextWrapper = ContextThemeWrapper(
                    this@ContentActivity,
                    R.style.popupMenuStyle
                )

                val popupMenu = PopupMenu(contextWrapper, it)

                popupMenu.inflate(menuResId)

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menuItemCommentChat -> {
                            val intent = Intent(this@ContentActivity, ChattingActivity::class.java)
                            startActivity(intent)
                        }

                        R.id.menuItemCommentDeclaration -> {
                            MaterialAlertDialogBuilder(
                                this@ContentActivity,
                                R.style.MyDialogTheme
                            ).apply {
                                setTitle("게시물 신고")
                                setMessage("이 게시물을 신고하시겠습니까?")
                                setNegativeButton("취소", null)
                                setPositiveButton("신고") { dialogInterface: DialogInterface, i: Int ->
                                    Snackbar.make(it, "신고를 완료했습니다.", Snackbar.LENGTH_SHORT).show()
                                }
                                show().apply {
                                    getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                                    getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
                                }
                            }
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