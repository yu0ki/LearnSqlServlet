<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/WEB-INF/templates/head.jsp" flush="true" />
	
	
	<jsp:include page="/WEB-INF/templates/header_admin.jsp" flush="true" />

		<div class="row mb-5"><h3>本当に退会しますか？</h3></div>
		<div class="row py-5">
			<div class="col-3"></div>
			<div class="col-4">
				<a href="/LearnSqlServlet/admins/home" class="btn btn-primary">退会しない</a>
			</div>
			<div class="col-4">
				<form method="post" action="./confirm_account_delete">
    				<div>
						<input type="submit" value="退会する" class="btn btn-danger">
					</div>
				</form>
			</div>
		</div>
		
		
	
		<jsp:include page="/WEB-INF/templates/footer.jsp" flush="true" />
</html>