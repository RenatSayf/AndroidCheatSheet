package com.renatsayf.androidcheatsheet.ui.sections.recyclerview.compose_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.renatsayf.androidcheatsheet.databinding.FragmentComposeListBinding
import com.renatsayf.androidcheatsheet.ui.sections.recyclerview.SimpleListViewModel
import com.renatsayf.androidcheatsheet.data.db.room.java.ArticleEntity

class ComposeListFragment : Fragment() {

    private lateinit var binding: FragmentComposeListBinding

    private val viewModel: SimpleListViewModel by lazy {
        val factory = SimpleListViewModel.Factory()
        ViewModelProvider(this, factory)[SimpleListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComposeListBinding.inflate(inflater, container, false)

        //region Hint Compose_Display_the_list_in_layout
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Scaffold {
                        ArticlesList()
                    }
                }
            }
        }
        //endregion Compose_Display_the_list_in_layout

        return binding.root
    }

    //region Hint Compose_Lazy_list
    @Preview
    @Composable
    fun ArticlesList() {

        val list = viewModel.getAllArticles().value
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            if (!list.isNullOrEmpty()) {
                items(list, itemContent = { item ->
                    ArticleItem(article = item)
                })
            }
        }
    }
    //endregion

    //region Hint Compose_Lazy_list_item
    @Composable
    fun ArticleItem(article: ArticleEntity) {

        MaterialTheme {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = article.header,
                            fontSize = 18.sp,
                            color = Color.Blue
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = article.content.trimMargin(),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                    }
                }
            }
        }
    }
    //endregion

}



