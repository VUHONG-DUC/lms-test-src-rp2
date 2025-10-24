package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// LMSへアクセス
		goTo("http://localhost:8080/lms/");
		//タイトル取得し、検証を行う
		String pageTitle = WebDriverUtils.webDriver.getTitle();
		assertEquals("ログイン | LMS", pageTitle);
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインID及びパスワード要素の取得
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		final WebElement loginPassword = webDriver.findElement(By.name("password"));
		// ログインID及びパスワードのクリア処理
		loginId.clear();
		loginPassword.clear();
		//ログイン情報入力処理
		loginId.sendKeys("StudentAA02");
		loginPassword.sendKeys("StudentAA021");
		// ログイン処理
		loginPassword.sendKeys(Keys.ENTER);
		pageLoadTimeout(10);
		//タイトルを確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		//small要素を取得し、ユーザー名を確認
		final WebElement smallElement = webDriver.findElement(By.tagName("small"));
		String userName = smallElement.getText();
		assertEquals("ようこそ受講生ＡＡ２さん", userName);
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 機能の要素を取得
		final WebElement toggleElement = webDriver.findElement(By.className("dropdown-toggle"));
		toggleElement.click();
		//ヘルプリンク要素を取得
		final WebElement helpLinkElement = webDriver.findElement(By.linkText("ヘルプ"));
		helpLinkElement.click();
		//遷移画面タイトル確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//よくある質問リンク要素を取得
		final WebElement faqElement = webDriver.findElement(By.linkText("よくある質問"));
		faqElement.click();
		//windowタブを取得
		ArrayList<String> windowTabs = new ArrayList<String>(webDriver.getWindowHandles());
		//よくある質問タブを選択
		webDriver.switchTo().window(windowTabs.get(1));
		//遷移画面タイトル確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		//カテゴリ検索でリンク要素を取得
		final WebElement categoriElement = webDriver.findElement(By.linkText("【研修関係】"));
		categoriElement.click();
		pageLoadTimeout(5);
		//よくある質問件数を取得
		scrollBy("250");
		final List<WebElement> fqaElements = webDriver.findElements(By.className("sorting_1"));
		String fisrtFqaTitle = fqaElements.get(0).getText();
		String secondFqaTitle = fqaElements.get(1).getText();
		//タイトルを確認
		assertEquals("Q.キャンセル料・途中退校について", fisrtFqaTitle);
		assertEquals("Q.研修の申し込みはどのようにすれば良いですか？", secondFqaTitle);
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		//よくある質問要素を取得
		final WebElement faqElement = WebDriverUtils.webDriver.findElement(By.className("sorting_1"));
		faqElement.click();
		//検索結果の回答内容を確認する
		final WebElement answerElement = webDriver.findElement(By.id("answer-h[${status.index}]"));
		String answerText = "A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。";
		assertEquals(answerText, answerElement.getText());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
