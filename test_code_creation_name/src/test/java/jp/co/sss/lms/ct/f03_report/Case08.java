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

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 「2025年7月9日（水）」の行にある要素を取得
		final WebElement rowElement = webDriver.findElement(By.xpath("//td[text()='2025年7月9日(水)']/.."));
		List<WebElement> rowsList = rowElement.findElements(By.tagName("td"));
		//レポート提出状況の要素を取得
		final WebElement reportElement = rowsList.get(2);
		String reportCheckString = reportElement.getText();
		//未提出の場合詳細要素を取得
		if (reportCheckString.equals("提出済み")) {
			final WebElement detailElement = rowsList.get(4);
			detailElement.click();
			pageLoadTimeout(10);
			//タイトル取得し、検証を行う
			String pageTitle = webDriver.getTitle();
			assertEquals("セクション詳細 | LMS", pageTitle);
			//エビデンス取得
			getEvidence(new Object() {
			});
		}
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「提出する」ボタン要素を取得
		scrollBy("200");
		final WebElement submitElement = webDriver.findElement(By.xpath("//*[@value='提出済み週報【デモ】を確認する']"));
		submitElement.click();
		pageLoadTimeout(10);
		//タイトル取得し、検証を行う
		String pageTitle = webDriver.getTitle();
		assertEquals("レポート登録 | LMS", pageTitle);
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
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
		fieldNameElement.sendKeys("テスト");
		fieldValueElement.sendKeys("3");
		achievementLevelElement.sendKeys("1");
		impressionsElement.sendKeys("難しかった");
		reviewOfTheWeekElement.sendKeys("知識が深まった");
		//提出処理
		scrollBy("300");
		final WebElement submitButtonElement = webDriver.findElement(By.className("btn-primary"));
		submitButtonElement.click();
		pageLoadTimeout(10);
		//遷移先のURLを確認
		assertEquals("http://localhost:8080/lms/section/detail?sectionId=2", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// 詳細要素を取得
		scrollBy("300");
		final WebElement dateElement = webDriver.findElement(By.xpath("//td[text()='2025年7月9日(水)']"));
		final WebElement detailElement = dateElement
				.findElement(By.xpath("./following-sibling::td//input[@value='詳細']"));
		detailElement.click();
		pageLoadTimeout(10);
		//遷移先のURLを確認
		assertEquals("http://localhost:8080/lms/report/detail", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
