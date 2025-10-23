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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//keywork要素を取得
		final WebElement keyWork = webDriver.findElement(By.name("keyword"));
		//クリア処理
		keyWork.clear();
		//入力処理
		keyWork.sendKeys("キャリア");
		//検索処理
		keyWork.sendKeys(Keys.ENTER);
		//タイトル確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		//検索欄に入力値を確認
		final WebElement inputElement = webDriver.findElement(By.id("form"));
		assertEquals("キャリア", inputElement.getAttribute("value"));
		// 画面スクロール
		scrollBy("window.innerHeight");
		pageLoadTimeout(5);
		//検索結果要素を取得
		final List<WebElement> searchFqaElements = webDriver.findElements(By.className("sorting_1"));
		String fqaTitleString = searchFqaElements.get(0).getText();
		assertEquals("Q.セルフ・キャリアドック制度とは何か", fqaTitleString);
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		//クリア要素を取得
		final WebElement clearElement = webDriver.findElement(By.xpath("//input[@value='クリア']"));
		clearElement.click();
		// 画面スクロール
		scrollTo("0");
		pageLoadTimeout(5);
		//keywork要素を取得
		final WebElement keyWork = webDriver.findElement(By.name("keyword"));
		assertEquals("", keyWork.getText());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
