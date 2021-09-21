package com.example.stomone.menuItems.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R
import com.example.stomone.TitleController
import com.example.stomone.databinding.FragmentNewsBinding
import com.example.stomone.menuItems.news.recyclerNews.NewsAdapter
import com.example.stomone.menuItems.news.recyclerNews.NewsItem
import com.example.stomone.menuItems.news.viewModel.NewsViewModel
import com.example.stomone.menuItems.picturesVisit.recyclerPicturesVisit.PicturesVisitItem
import com.example.stomone.menuItems.picturesVisit.viewModel.PicturesVisitViewModel
import com.example.stomone.menuItems.xrays.fragment.XRaysFragment
import com.example.stomone.room.news.RNews
import com.example.stomone.room.picturesVisit.RPicturesVisit
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: NewsViewModel by viewModels {
        viewModelFactory
    }

    lateinit var anima: View
    private lateinit var starAnim: Animation
    private lateinit var binding: FragmentNewsBinding
    private var adapter: NewsAdapter? = null
    private var list = ArrayList<NewsItem>()
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? TitleController)?.setTitle(
            requireContext().resources.getString(R.string.drawer_menu_news)
        )
        initAnimationView()
        viewModel.isAnimation(true)
        initRecycler()
        observeViewModel()
        viewModel.getNews()

    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = binding.idNewsAnim
    }

    private fun observeViewModel() {
        viewModel.readAllNewsLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<List<RNews>> { result ->
                list.clear()
                result.forEach { item ->
                    list.add(
                        NewsItem(
                            item.title,
                            item.content,
                            item.imagePath
                        )
                    )
                }
                recyclerView?.adapter?.notifyDataSetChanged()
            })

        viewModel.booleanAnimation.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Boolean> { bool ->
                Observable.just(animation(bool))
                    .observeOn(Schedulers.newThread())
                    .subscribe()
            })
    }

    private fun animation(bool: Boolean) {
        if (bool) {
            anima.visibility = View.VISIBLE
            anima.startAnimation(starAnim)
        } else {
            anima.visibility = View.INVISIBLE
            starAnim.cancel()
            anima.clearAnimation()
        }
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(
            LayoutInflater.from(requireContext()),
            list
        ) { newsItem: NewsItem, position: Int ->
            (activity as? NewsFragment.OnNewsClickListener)?.onNewsClick(newsItem)
        }
        recyclerView = binding.idRecyclerViewNews
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    interface OnNewsClickListener {
        fun onNewsClick(newsItem: NewsItem)
    }

}