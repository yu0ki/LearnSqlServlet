<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/WEB-INF/templates/head.jsp" flush="true" />
	
	
	
		<jsp:include page="/WEB-INF//templates/headerout.jsp" flush="true" />
		
		<form action="./log_in" method="post">
		<div class="field py-3">
			<label>管理者番号</label>：<input type="text" name="admin_number" required autofocus>
		</div>
		
		<div class="field py3">
			<label>担当内容</label> : <select name="responsibility" required>
										<option value="">選択してください</option>
										<option value="ストーリー">ストーリー</option>
										<option value="問題">問題</option>
										<option value="告知">告知</option>
										</select>
		</div>
		
		<div class="py-3">
			<input type="submit" value="ログイン" class="btn btn-success">
		</div>
		</form>
		
		
		<p>
		アカウント登録がお済みでない方はこちらへ↓<br>
		<a href="/LearnSqlServlet/admins/sign_up" class="btn btn-primary">新規登録</a>
		</p>
		
	
		<jsp:include page="/WEB-INF/templates/footer.jsp" flush="true" />
</html>