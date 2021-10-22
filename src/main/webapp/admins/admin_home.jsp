<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />

		<div class="row"><h3>管理者情報</h3></div>
		<div class="row">			
			<table class="table table-bordered">
				<tr>
					<th class="bg-light">管理者番号</th>
					<td>
						<% beans.AdminAccountBeans admin = (beans.AdminAccountBeans) session.getAttribute("admin"); %>
						<% out.println(admin.getAdminNumber()); %> 					
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">管理者氏名</th>
					<td>
						<% out.println(admin.getName()); %> 					
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">担当内容</th>
					<td>
						<% out.println(admin.getResponsibility()); %> 					
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">連絡先</th>
					<td>
						<% out.println(admin.getContact()); %> 					
					</td>
				</tr>
			</table>
		</div>
		
		<div class="row py-3">
			<!--編集ボタンと退会ボタン -->
			<div class="col-3"></div>
			<div class="col-4">
				<a href="/LearnSqlServlet/admins/account/edit" class="btn btn-success">編集する</a>
			</div>
			<div class="col-5">
				<a href="/LearnSqlServlet/admins/confirm_account_delete" class="btn btn-danger">退会する</a>
			</div>
			
		</div>
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>