<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/WEB-INF/templates/head.jsp" flush="true" />
	
	
	
		<jsp:include page="/WEB-INF//templates/headerout.jsp" flush="true" />
		
		<form action="./sign_up" method="post">
		<div class="field py-3">
			<label>管理者番号</label>：<input type="text" name="admin_number" required autofocus>
		</div>
		
		<div class="field py-3">
			<label>氏名</label>：<input type="text" name="name" required>
		</div>
		
		<div class="filed py-3">
			<label>担当内容</label> : <select name="responsibility" required>
										<option value="">選択してください</option>
										<option value="ストーリー">ストーリー</option>
										<option value="問題">問題</option>
										<option value="告知">告知</option>
										</select>
		</div>
		
		<div class="field py-3">
			<label>連絡先</label> : <input type="text" name="contact" required>
		</div>
		
		<div class="py-3">
			<input type="submit" value="新規登録" class="btn btn-success">
		</div>
		</form>
		
		
		<p>
		アカウント登録がお済の方はこちらへ↓<br>
		<a href="/LearnSqlServlet/admins/log_in"><button class="btn btn-primary">ログイン</button></a>
		</p>
		
	
		<jsp:include page="/WEB-INF/templates/footer.jsp" flush="true" />
</html>