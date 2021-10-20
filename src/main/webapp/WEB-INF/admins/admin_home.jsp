<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/WEB-INF/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/WEB-INF/templates/header_admin.jsp" flush="true" />

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
		
		<div class="row">
			<!--編集ボタンと退会ボタン -->
		</div>
		
	
		<jsp:include page="/WEB-INF/templates/footer.jsp" flush="true" />
</html>