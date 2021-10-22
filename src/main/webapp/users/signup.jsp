<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
		<jsp:include page="/templates/headerout.jsp" flush="true" />
		
		<form action="./sign_up" method="post">
		<div class="field py-3">
			<label>ニックネーム</label>：<input type="text" name="nickname" required>
		</div>
		
		<div class="py-3">
			<input type="submit" value="新規登録" class="btn btn-success">
		</div>
		</form>
		
		
		<p>
		アカウント登録がお済の方はこちらへ↓<br>
		<a href="/LearnSqlServlet/log_in"><button class="btn btn-primary">ログイン</button></a>
		</p>
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>