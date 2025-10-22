package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

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
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// ログインID及びパスワード要素の取得
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		final WebElement loginPassword = webDriver.findElement(By.name("password"));
		// ログインID及びパスワードのクリア処理
		loginId.clear();
		loginPassword.clear();
		//ログイン情報入力処理
		loginId.sendKeys("TestAA001");
		loginPassword.sendKeys("TestAA001");
		// ログイン処理
		loginPassword.sendKeys(Keys.ENTER);
		//エラーメッセージ、タイトルを確認
		final WebElement errorMsg = WebDriverUtils.webDriver.findElement(By.cssSelector(".help-inline.error"));
		assertEquals("* ログインに失敗しました。", errorMsg.getText());
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
