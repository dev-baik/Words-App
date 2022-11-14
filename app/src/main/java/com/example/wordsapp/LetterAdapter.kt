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

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.ItemViewBinding

class LetterAdapter : RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {
    private lateinit var binding: ItemViewBinding
    // 'A'에서 'Z'까지 [CharRange]를 생성하고 목록으로 변환합니다.
    private val list = ('A').rangeTo('Z').toList()

    class LetterViewHolder(private val binding: ItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Char) {
            binding.buttonItem.text = item.toString()

            binding.buttonItem.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("letter", binding.buttonItem.text.toString())
                binding.root.context.startActivity(intent)
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
            // With `null` as the second argument to [AccessibilityAction], the
            // accessibility service announces "double tap to activate".
            // If a custom string is provided,
            // it announces "double tap to <custom string>".
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