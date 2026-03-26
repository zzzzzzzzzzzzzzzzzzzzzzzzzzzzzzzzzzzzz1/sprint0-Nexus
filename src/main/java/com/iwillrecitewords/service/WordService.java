package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.util.FileUtil;

import java.util.List;
import java.util.Random;

/**
 * 单词服务：从本地词库加载单词，提供随机获取能力
 */
public class WordService {
    private final List<Word> wordLibrary;
    private final Random random;

    public WordService() {
        // 从本地文件加载词库
        this.wordLibrary = FileUtil.loadWordLibrary();
        this.random = new Random();
    }

    /**
     * 随机获取一个单词
     */
    public Word getRandomWord() {
        if (wordLibrary.isEmpty()) {
            return new Word("无词库", "/", "adj.", "请检查红宝书.txt文件", "No word library", "请配置词库");
        }
        int index = random.nextInt(wordLibrary.size());
        return wordLibrary.get(index);
    }

    /**
     * 获取词库总单词数
     */
    public int getTotalWordCount() {
        return wordLibrary.size();
    }
}