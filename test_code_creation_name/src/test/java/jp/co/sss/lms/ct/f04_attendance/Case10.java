package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		// 勤怠リンク要素を取得
		final WebElement attendancElement = webDriver.findElement(By.linkText("勤怠"));
		attendancElement.click();
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		pageLoadTimeout(10);
		//タイトル取得し、検証を行う
		String pageTitle = WebDriverUtils.webDriver.getTitle();
		assertEquals("勤怠情報変更｜LMS", pageTitle);
		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		// 出勤要素を取得
		final WebElement punchInElement = webDriver.findElement(By.name("punchIn"));
		punchInElement.click();
		//確認ダイアログ操作
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		//出勤登録完了のメッセージ要素を取得
		final WebElement creatCompleteElement = webDriver
				.findElement(By.xpath("//div[contains(@class, 'alert') and contains(@class, 'alert-info')]/span"));
		assertEquals("勤怠情報の登録が完了しました。", creatCompleteElement.getText());
		//出勤時間が登録されているかを確認
		final WebElement punchInCheck = webDriver
				.findElement(By.xpath("//div[@id='main']/div[@class='row']//tbody/tr[@class='info']/td[3]"));
		assertTrue(!punchInCheck.getText().equals(""));
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		// 退勤要素を取得
		final WebElement punchOutElement = webDriver.findElement(By.name("punchOut"));
		punchOutElement.click();
		//確認ダイアログ操作
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		//出勤登録完了のメッセージ要素を取得
		final WebElement creatCompleteElement = webDriver
				.findElement(By.xpath("//div[contains(@class, 'alert') and contains(@class, 'alert-info')]/span"));
		assertEquals("勤怠情報の登録が完了しました。", creatCompleteElement.getText());
		//退勤時間が登録されているかを確認
		final WebElement punchOutCheck = webDriver
				.findElement(By.xpath("//div[@id='main']/div[@class='row']//tbody/tr[@class='info']/td[4]"));
		assertTrue(!punchOutCheck.getText().equals(""));
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
