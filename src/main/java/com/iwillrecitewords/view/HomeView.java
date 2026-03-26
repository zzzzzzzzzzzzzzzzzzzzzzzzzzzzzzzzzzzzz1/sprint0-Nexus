package com.iwillrecitewords.view;

import com.iwillrecitewords.MainUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 主页面：适配电脑宽屏，修复点击交互问题
 */
public class HomeView {
    private final Stage stage;

    public HomeView(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        // 根布局：适配宽屏的垂直流式布局
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 240, 235), // 柔和护眼暖底色
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        // ====================== 顶部：签到卡片 ======================
        VBox topContainer = new VBox(20);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setPadding(new Insets(40, 0, 20, 0));

        // 签到卡片：适配宽屏尺寸
        StackPane signInCard = new StackPane();
        signInCard.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.7),
                new CornerRadii(25),
                Insets.EMPTY
        )));
        signInCard.setPrefSize(400, 180);

        VBox signInContent = new VBox(10);
        signInContent.setAlignment(Pos.CENTER);
        Label signInIcon = new Label("📅");
        signInIcon.setFont(Font.font(36));
        Label signInTitle = new Label("每日签到");
        signInTitle.setFont(Font.font("System", FontWeight.BOLD, 32));
        Label dateLabel = new Label(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 EEEE")));
        dateLabel.setFont(Font.font(18));
        dateLabel.setTextFill(Color.rgb(80, 80, 80));
        signInContent.getChildren().addAll(signInIcon, signInTitle, dateLabel);
        signInCard.getChildren().add(signInContent);

        topContainer.getChildren().add(signInCard);
        root.setTop(topContainer);

        // ====================== 中间：核心功能按钮 ======================
        HBox centerContainer = new HBox(80);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setPadding(new Insets(20, 0, 40, 0));

        // 1. Learn学习按钮卡片：全区域可点击
        StackPane learnCard = createFunctionCard(
                "📚 开始学习",
                "Learn",
                String.valueOf(MainUI.STAT_SERVICE.getLearnedCount()),
                Color.rgb(255, 110, 30)
        );
        // 点击事件：跳转到背单词页面
        Button learnBtn = (Button) learnCard.getUserData();
        learnBtn.setOnAction(e -> {
            LearnView learnView = new LearnView(stage);
            stage.setScene(learnView.getScene());
        });

        // 2. Review复习按钮卡片：全区域可点击
        StackPane reviewCard = createFunctionCard(
                "📖 错词复习",
                "Review",
                String.valueOf(MainUI.STAT_SERVICE.getReviewCount()),
                Color.rgb(30, 130, 255)
        );

        centerContainer.getChildren().addAll(learnCard, reviewCard);
        root.setCenter(centerContainer);

        // ====================== 底部：导航栏 ======================
        HBox bottomNav = new HBox(200);
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setPadding(new Insets(30, 0, 40, 0));
        bottomNav.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.5),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        Label homeNav = new Label("🏠 首页");
        homeNav.setFont(Font.font("System", FontWeight.BOLD, 20));
        homeNav.setTextFill(Color.rgb(255, 110, 30));
        Label bookNav = new Label("📕 词库");
        bookNav.setFont(Font.font("System", FontWeight.BOLD, 20));
        bookNav.setTextFill(Color.rgb(100, 100, 100));
        Label statNav = new Label("📊 统计");
        statNav.setFont(Font.font("System", FontWeight.BOLD, 20));
        statNav.setTextFill(Color.rgb(100, 100, 100));

        bottomNav.getChildren().addAll(homeNav, bookNav, statNav);
        root.setBottom(bottomNav);

        return new Scene(root);
    }

    /**
     * 创建功能卡片：全区域可点击，适配宽屏尺寸
     */
    private StackPane createFunctionCard(String subTitle, String title, String number, Color themeColor) {
        StackPane card = new StackPane();
        // 卡片背景
        card.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.7),
                new CornerRadii(25),
                Insets.EMPTY
        )));
        card.setPrefSize(350, 250);

        // 卡片内容
        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        Label subTitleLabel = new Label(subTitle);
        subTitleLabel.setFont(Font.font(18));
        subTitleLabel.setTextFill(Color.rgb(100, 100, 100));
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 48));
        Label numLabel = new Label("已学习：" + number + " 词");
        numLabel.setFont(Font.font(24));
        numLabel.setTextFill(themeColor);
        content.getChildren().addAll(subTitleLabel, titleLabel, numLabel);

        // 透明点击按钮：覆盖整个卡片，实现全区域点击
        Button clickBtn = new Button();
        clickBtn.setPrefSize(350, 250);
        clickBtn.setOpacity(0); // 完全透明，不影响视觉
        clickBtn.setCursor(javafx.scene.Cursor.HAND); // 鼠标悬浮变手型，提示可点击

        // 把内容和按钮叠加到卡片里
        card.getChildren().addAll(content, clickBtn);
        card.setUserData(clickBtn); // 把按钮存起来，外部绑定事件

        return card;
    }
}