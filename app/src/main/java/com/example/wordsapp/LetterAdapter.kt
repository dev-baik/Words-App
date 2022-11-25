/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordsapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.ItemViewBinding

class LetterAdapter(val context: Context) : RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {
    private lateinit var binding: ItemViewBinding
    // 'A'에서 'Z'까지 [CharRange]를 생성하고 목록으로 변환합니다.
    private val list = ('A').rangeTo('Z').toList()

    inner class LetterViewHolder(private val binding: ItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Char) {
            binding.buttonItem.text = item.toString()

            binding.buttonItem.setOnClickListener {
                val action = LetterListFragmentDirections.actionLetterListFragmentToWordListFragment(letter = binding.buttonItem.text.toString())
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
//        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
//        layout.accessibilityDelegate = Accessibility
//        return LetterViewHolder(layout)

        binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // 접근성 서비스로 읽을 텍스트를 설정하기 위해 사용자 지정 접근성 대리자를 설정합니다.
    companion object Accessibility : View.AccessibilityDelegate() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfo
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // 1. [AccessibilityAction]의 두 번째 인수로 'null'을 사용하면
            // 접근성 서비스가 "활성화하려면 두 번 탭하세요"를 알립니다.
            // 2. 사용자 지정 문자열이 제공되면 "<custom string>을 두 번 탭합니다."라고 알립니다.
            val customString = host.context?.getString(R.string.look_up_words)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info.addAction(customClick)
        }
    }
}