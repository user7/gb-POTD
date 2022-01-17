package com.geekbrains.potd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.geekbrains.potd.R
import com.geekbrains.potd.databinding.FragmentNavBinding
import com.geekbrains.potd.fragments.bookmarks.Bookmark
import com.geekbrains.potd.fragments.bookmarks.BookmarksFragment
import com.geekbrains.potd.fragments.earth.EarthFragment
import com.geekbrains.potd.fragments.mars.MarsFragment
import com.geekbrains.potd.fragments.system.SystemFragment

class NavFragment : Fragment() {
    private var _binding: FragmentNavBinding? = null
    private val binding: FragmentNavBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val navFragments = mapOf(
        Pair(R.id.bottomViewSystem) { SystemFragment() },
        Pair(R.id.bottomViewEarth) { EarthFragment() },
        Pair(R.id.bottomViewMars) { MarsFragment() },
        Pair(R.id.bottomViewBookmarks) { BookmarksFragment(this::navigateByBookmark) },
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bottomNavigationView.setOnItemSelectedListener { navigateByMenuItem(it.itemId) }
        binding.bottomNavigationView.selectedItemId = R.id.bottomViewSystem
        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigateTo(frag: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.photosContainer, frag)
            .commit()
    }

    private fun navigateByMenuItem(itemId: Int): Boolean {
        val fragmentFactory = navFragments[itemId]
        if (fragmentFactory != null) {
            navigateTo(fragmentFactory())
            return true
        }
        return false
    }

    private fun navigateByBookmark(bookmark: Bookmark) {
        when (bookmark) {
            is Bookmark.Potd -> navFragments[R.id.bottomViewSystem]
            else -> null
        }?.let { fragmentFactory ->
            val frag = fragmentFactory()
            frag.startingBookmark = bookmark
            navigateTo(frag)
        }
    }
}