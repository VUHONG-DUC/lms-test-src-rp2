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
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		loginId.sendKeys("StudentAA03");
		loginPassword.sendKeys("StudentAA031");
		// ログイン処理
		loginPassword.sendKeys(Keys.ENTER);
		pageLoadTimeout(10);
		//タイトルを確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		//small要素を取得し、ユーザー名を確認
		final WebElement smallElement = webDriver.findElement(By.tagName("small"));
		String userName = smallElement.getText();
		assertEquals("ようこそ受講生ＡＡ３さん", userName);
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 「2025年10月6日（月）」の行にある要素を取得
		final WebElement rowElement = webDriver.findElement(By.xpath("//td[text()='2025年10月6日(月)']/.."));
		List<WebElement> rowsList = rowElement.findElements(By.tagName("td"));
		//レポート提出状況の要素を取得
		final WebElement reportElement = rowsList.get(2);
		String reportCheckString = reportElement.getText();
		//未提出の場合詳細要素を取得
		if (reportCheckString.equals("未提出")) {
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
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「提出する」ボタン要素を取得
		final WebElement submitElement = webDriver.findElement(By.xpath("//input[@value='日報【デモ】を提出する']"));
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
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// レポート入力要素を取得
		final WebElement reportInputElement = webDriver.findElement(By.className("form-control"));
		reportInputElement.clear();
		reportInputElement.sendKeys("今日はよくできました。");
		// 「提出する」ボタン要素を取得
		final WebElement submitElement = webDriver.findElement(By.className("btn-primary"));
		submitElement.click();
		pageLoadTimeout(10);
		//タイトル取得し、検証を行う
		String pageTitle = webDriver.getTitle();
		assertEquals("セクション詳細 | LMS", pageTitle);
		//提出するボタン名称が変更されているかを確認
		final WebElement submitButtonElement = webDriver.findElement(By.xpath("//*[@class='btn btn-default']"));
		String submitButtonValue = submitButtonElement.getAttribute("value");
		assertEquals("提出済み日報【デモ】を確認する", submitButtonValue);
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
