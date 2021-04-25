package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public BoardDAO(Connection con) {
		this.con = con;
	}
	
	//전체조회
	public ArrayList<BoardDTO> sltMulti(int start, int rows) throws Exception{
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		String all = "SELECT *		            		"
				  + "FROM ( SELECT ROWNUM RNUM			"
				  + "			 , O.*					"
				  + "		FROM ( SELECT *				"
				  + "			   FROM	  BOARD			"
				  +	"			   ORDER BY REF DESC	"
				  + "					,RE_STEP ASC	"
				  + "			 ) O					"
				  +	"		)							"
				  + "WHERE 	 RNUM   >=?		 			"
				  + "AND 	 ROWNUM <=?		 			";
		
		pstmt = con.prepareStatement(all);
		pstmt.setInt(1, start);
		pstmt.setInt(2, rows);

		rs = pstmt.executeQuery();

		while(rs.next())
		{
			BoardDTO boardDto = new BoardDTO();
			boardDto.setNum			(rs.getInt("num"));
			boardDto.setWriter		(rs.getString("writer"));
			boardDto.setEmail		(rs.getString("email"));
			boardDto.setSubject		(rs.getString("subject"));
			boardDto.setPasswd		(rs.getString("passwd"));
			boardDto.setReg_date	(rs.getTimestamp("reg_date"));
			boardDto.setReadcount	(rs.getInt("readcount"));
			boardDto.setRef			(rs.getInt("ref"));
			boardDto.setRe_step		(rs.getInt("re_step"));
			boardDto.setRe_level	(rs.getInt("re_level"));
			boardDto.setContent		(rs.getString("content"));
			boardDto.setIp			(rs.getString("ip"));

			list.add(boardDto);

		}
		
		BoardCommon.dbClose(rs);
		BoardCommon.dbClose(pstmt);

		return list;
	}
	
	//단건조회
	public BoardDTO sltOne(int num) throws Exception {
		
		BoardDTO boardDto = new BoardDTO();
		String one = "SELECT * FROM BOARD WHERE NUM = ?";
		
		pstmt = con.prepareStatement(one);
		pstmt.setInt(1, num);

		rs = pstmt.executeQuery();

		while(rs.next())
		{
			boardDto.setNum			(rs.getInt("num"));
			boardDto.setWriter		(rs.getString("writer"));
			boardDto.setEmail		(rs.getString("email"));
			boardDto.setSubject		(rs.getString("subject"));
			boardDto.setPasswd		(rs.getString("passwd"));
			boardDto.setReg_date	(rs.getTimestamp("reg_date"));
			boardDto.setReadcount	(rs.getInt("readcount"));
			boardDto.setRef			(rs.getInt("ref"));
			boardDto.setRe_step		(rs.getInt("re_step"));
			boardDto.setRe_level	(rs.getInt("re_level"));
			boardDto.setContent		(rs.getString("content"));
			boardDto.setIp			(rs.getString("ip"));

		}
		
		BoardCommon.dbClose(rs);
		BoardCommon.dbClose(pstmt);
		
		return boardDto;
	}
	
	//총건수
	public int mulCount() throws Exception {
		
		String count = "SELECT COUNT(*) FROM BOARD";
		int cnt = 0;
		
		pstmt = con.prepareStatement(count);
		
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			cnt = rs.getInt(1);
		}
		
		BoardCommon.dbClose(rs);
		BoardCommon.dbClose(pstmt);
		
		return cnt;
	}
	
	//입력
	public int insert(BoardDTO dto) throws Exception {
		
		String insertArti = "INSERT INTO BOARD(NUM, WRITER, EMAIL, SUBJECT, "
				+ "PASSWD, REG_DATE, REF, RE_STEP, RE_LEVEL, CONTENT, IP)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		int ret = 0;
		
		pstmt = con.prepareStatement(insertArti);
		pstmt.setInt		(1, dto.getNum());
		pstmt.setString		(2, dto.getWriter());
		pstmt.setString		(3, dto.getEmail());
		pstmt.setString		(4, dto.getSubject());
		pstmt.setString		(5, dto.getPasswd());
		pstmt.setTimestamp	(6, dto.getReg_date());
		pstmt.setInt		(7, dto.getRef());
		pstmt.setInt		(8, dto.getRe_step());
		pstmt.setInt		(9, dto.getRe_level());
		pstmt.setString		(10, dto.getContent());
		pstmt.setString		(11, dto.getIp());
		
		ret = pstmt.executeUpdate();
		
		BoardCommon.dbClose(pstmt);
		
		return ret;
	}
	
	//맥스 글번호
	public int maxArtiNum() throws Exception {
		
		String mexNum = "SELECT MAX(NUM) FROM BOARD";

		int number = 0;
		
		pstmt = con.prepareStatement(mexNum);
		rs = pstmt.executeQuery();
		
		if(rs.next())
		{
			number = rs.getInt(1) + 1;
		}
		else
		{
			number = 1;
		}
		
		BoardCommon.dbClose(rs);
		BoardCommon.dbClose(pstmt);
		
		return number;
	}
	
	//댓글ref처리
	public int insertArtiRe(BoardDTO dto) throws Exception {
		
		String updateNum = "UPDATE BOARD SET RE_STEP = RE_STEP + 1"
				+ "WHERE REF = ? AND RE_STEP > ?";
		
		int cnt = 0;
		
		pstmt = con.prepareStatement(updateNum);
		
		pstmt.setInt(1, dto.getRef());
		pstmt.setInt(2, dto.getRe_step());
		
		cnt = pstmt.executeUpdate();
		
		BoardCommon.dbClose(pstmt);
		
		return cnt;
	}
	
	//수정
	public int updateArti(BoardDTO dto) throws Exception {
		
		String update = "UPDATE BOARD SET WRITER = ?, EMAIL = ?, SUBJECT = ?, "
				+ "PASSWD = ?, CONTENT = ? WHERE NUM = ?";
		int ret = 0;
		pstmt = con.prepareStatement(update);
		
		pstmt.setString(1, dto.getWriter());
		pstmt.setString(2, dto.getEmail());
		pstmt.setString(3, dto.getSubject());
		pstmt.setString(4, dto.getPasswd());
		pstmt.setString(5, dto.getContent());
		pstmt.setInt   (6, dto.getNum());
		
		ret = pstmt.executeUpdate();
		
		BoardCommon.dbClose(pstmt);
		
		return ret;
	}
	
	//삭제
	public int deleteArti(int num) throws Exception {
		
		String delete = "DELETE FROM BOARD WHERE NUM = ?";
		int ret = 0;
		
		pstmt = con.prepareStatement(delete);
		
		pstmt.setInt(1, num);
		
		ret = pstmt.executeUpdate();
		
		BoardCommon.dbClose(pstmt);
		
		return ret;
	}
	
	//조회수
	public void updateReadCount(int num) throws Exception {
		
		String read = "UPDATE BOARD SET READCOUNT = READCOUNT + 1"
				+ "WHERE NUM = ?";
		
		pstmt = con.prepareStatement(read);
		pstmt.setInt(1, num);
		
		pstmt.executeUpdate();
		
		BoardCommon.dbClose(pstmt);
	}

}
