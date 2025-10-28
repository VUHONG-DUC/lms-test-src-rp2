package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

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
import org.openqa.selenium.support.ui.Select;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		//small要素を取得し、ユーザー名を確認
		final WebElement smallElement = webDriver.findElement(By.tagName("small"));
		smallElement.click();
		pageLoadTimeout(10);
		//遷移先のURLを確認
		assertEquals("http://localhost:8080/lms/user/detail", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 修正要素を取得
		scrollBy("300");
		final WebElement dateElement = webDriver.findElement(By.xpath("//td[text()='2025年7月9日(水)']"));
		final WebElement detailElement = dateElement
				.findElement(By.xpath("./following-sibling::td//input[@value='修正する']"));
		detailElement.click();
		pageLoadTimeout(10);
		//遷移先のURLを確認
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// レポート各カラムを取得
		final WebElement fieldNameElement = webDriver.findElement(By.id("intFieldName_0"));
		final WebElement fieldValueElement = webDriver.findElement(By.id("intFieldValue_0"));
		final WebElement achievementLevelElement = webDriver.findElement(By.id("content_0"));
		final WebElement impressionsElement = webDriver.findElement(By.id("content_1"));
		final WebElement reviewOfTheWeekElement = webDriver.findElement(By.id("content_2"));
		//クリア処理
		fieldNameElement.clear();
		achievementLevelElement.clear();
		impressionsElement.clear();
		reviewOfTheWeekElement.clear();
		//項目入力
		fieldValueElement.sendKeys("1");
		achievementLevelElement.sendKeys("1");
		impressionsElement.sendKeys("理解できた");
		reviewOfTheWeekElement.sendKeys("テスト");
		//提出処理
		scrollBy("300");
		final WebElement submitButtonElement = webDriver.findElement(By.className("btn-primary"));
		submitButtonElement.click();
		//遷移先、エラーメッセージを確認
		pageLoadTimeout(10);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		final WebElement errElement = webDriver.findElement(By.xpath("//p[@class='help-inline error']/span"));
		assertEquals("* 理解度を入力した場合は、学習項目は必須です。", errElement.getText());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// レポート各カラムを取得
		final WebElement fieldNameElement = webDriver.findElement(By.id("intFieldName_0"));
		final Select dropdown = new Select(webDriver.findElement(By.id("intFieldValue_0")));
		final WebElement achievementLevelElement = webDriver.findElement(By.id("content_0"));
		final WebElement impressionsElement = webDriver.findElement(By.id("content_1"));
		final WebElement reviewOfTheWeekElement = webDriver.findElement(By.id("content_2"));
		//クリア処理
		fieldNameElement.clear();
		achievementLevelElement.clear();
		impressionsElement.clear();
		reviewOfTheWeekElement.clear();
		//項目入力
		fieldNameElement.sendKeys("テスト");
		dropdown.selectByIndex(0);
		achievementLevelElement.sendKeys("1");
		impressionsElement.sendKeys("理解できた");
		reviewOfTheWeekElement.sendKeys("テスト");
		//提出処理
		scrollBy("300");
		final WebElement submitButtonElement = webDriver.findElement(By.className("btn-primary"));
		submitButtonElement.click();
		//遷移先、エラーメッセージを確認
		pageLoadTimeout(10);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		final WebElement errElement = webDriver.findElement(By.xpath("//p[@class='help-inline error']/span"));
		assertEquals("* 学習項目を入力した場合は、理解度は必須です。", errElement.getText());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// レポート各カラムを取得
		final WebElement fieldNameElement = webDriver.findElement(By.id("intFieldName_0"));
		final WebElement fieldValueElement = webDriver.findElement(By.id("intFieldValue_0"));
		final WebElement achievementLevelElement = webDriver.findElement(By.id("content_0"));
		final WebElement impressionsElement = webDriver.findElement(By.id("content_1"));
		final WebElement reviewOfTheWeekElement = webDriver.findElement(By.id("content_2"));
		//クリア処理
		fieldNameElement.clear();
		achievementLevelElement.clear();
		impressionsElement.clear();
		reviewOfTheWeekElement.clear();
		//項目入力
		fieldNameElement.sendKeys("テスト");
		fieldValueElement.sendKeys("1");
		achievementLevelElement.sendKeys("AAA");
		impressionsElement.sendKeys("理解できた");
		reviewOfTheWeekElement.sendKeys("テスト");
		//提出処理
		scrollBy("300");
		final WebElement submitButtonElement = webDriver.findElement(By.className("btn-primary"));
		submitButtonElement.click();
		//遷移先、エラーメッセージを確認
		pageLoadTimeout(10);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		final WebElement errElement = webDriver.findElement(By.xpath("//p[@class='help-inline error']/span"));
		assertEquals("* 目標の達成度は半角数字で入力してください。", errElement.getText());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// レポート各カラムを取得
		final WebElement fieldNameElement = webDriver.findElement(By.id("intFieldName_0"));
		final WebElement fieldValueElement = webDriver.findElement(By.id("intFieldValue_0"));
		final WebElement achievementLevelElement = webDriver.findElement(By.id("content_0"));
		final WebElement impressionsElement = webDriver.findElement(By.id("content_1"));
		final WebElement reviewOfTheWeekElement = webDriver.findElement(By.id("content_2"));
		//クリア処理
		fieldNameElement.clear();
		achievementLevelElement.clear();
		impressionsElement.clear();
		reviewOfTheWeekElement.clear();
		//項目入力
		fieldNameElement.sendKeys("テスト");
		fieldValueElement.sendKeys("1");
		achievementLevelElement.sendKeys("11");
		impressionsElement.sendKeys("理解できた");
		reviewOfTheWeekElement.sendKeys("テスト");
		//提出処理
		scrollBy("300");
		final WebElement submitButtonElement = webDriver.findElement(By.className("btn-primary"));
		submitButtonElement.click();
		//遷移先、エラーメッセージを確認
		pageLoadTimeout(10);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		final WebElement errElement = webDriver.findElement(By.xpath("//p[@class='help-inline error']/span"));
		assertEquals("* 目標の達成度は、半角数字で、1～10の範囲内で入力してください。", errElement.getText());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// レポート各カラムを取得
		final WebElement fieldNameElement = webDriver.findElement(By.id("intFieldName_0"));
		final WebElement fieldValueElement = webDriver.findElement(By.id("intFieldValue_0"));
		final WebElement achievementLevelElement = webDriver.findElement(By.id("content_0"));
		final WebElement impressionsElement = webDriver.findElement(By.id("content_1"));
		final WebElement reviewOfTheWeekElement = webDriver.findElement(By.id("content_2"));
		//クリア処理
		fieldNameElement.clear();
		achievementLevelElement.clear();
		impressionsElement.clear();
		reviewOfTheWeekElement.clear();
		//項目入力
		fieldNameElement.sendKeys("テスト");
		fieldValueElement.sendKeys("1");
		reviewOfTheWeekElement.sendKeys("テスト");
		//提出処理
		scrollBy("300");
		final WebElement submitButtonElement = webDriver.findElement(By.className("btn-primary"));
		submitButtonElement.click();
		//遷移先、エラーメッセージを確認
		pageLoadTimeout(10);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		List<WebElement> errElement = webDriver.findElements(By.xpath("//p[@class='help-inline error']/span"));
		//達成度エラーメッセージ
		String achievementLevelErr = errElement.get(0).getText();
		assertEquals("* 目標の達成度は半角数字で入力してください。", achievementLevelErr);
		//所感エラーメッセージ
		String impressionsErr = errElement.get(1).getText();
		assertEquals("* 所感は必須です。", impressionsErr);
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// レポート各カラムを取得
		final WebElement fieldNameElement = webDriver.findElement(By.id("intFieldName_0"));
		final WebElement fieldValueElement = webDriver.findElement(By.id("intFieldValue_0"));
		final WebElement achievementLevelElement = webDriver.findElement(By.id("content_0"));
		final WebElement impressionsElement = webDriver.findElement(By.id("content_1"));
		final WebElement reviewOfTheWeekElement = webDriver.findElement(By.id("content_2"));
		//2000文字以上を設定
		String impressionsInput = "a".repeat(2001);
		String reviewOfTheWeekInput = "a".repeat(2001);
		//クリア処理
		fieldNameElement.clear();
		achievementLevelElement.clear();
		impressionsElement.clear();
		reviewOfTheWeekElement.clear();
		//項目入力
		fieldNameElement.sendKeys("テスト");
		fieldValueElement.sendKeys("1");
		achievementLevelElement.sendKeys("1");
		impressionsElement.sendKeys(impressionsInput);
		reviewOfTheWeekElement.sendKeys(reviewOfTheWeekInput);
		//提出処理
		scrollBy("300");
		final WebElement submitButtonElement = webDriver.findElement(By.className("btn-primary"));
		submitButtonElement.click();
		//遷移先、エラーメッセージを確認
		pageLoadTimeout(10);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		List<WebElement> errElement = webDriver.findElements(By.xpath("//p[@class='help-inline error']/span"));
		//達成度エラーメッセージ
		String achievementLevelErr = errElement.get(0).getText();
		assertEquals("* 所感の長さが最大値(2000)を超えています。", achievementLevelErr);
		//所感エラーメッセージ
		String impressionsErr = errElement.get(1).getText();
		assertEquals("* 一週間の振り返りの長さが最大値(2000)を超えています。", impressionsErr);
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
