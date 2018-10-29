package com.oyun.media.transService;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-10-23 15:18
 **/
public class Test {
    public static void main(String[] args) throws  Exception_Exception {
        PortFuntionService service = new PortFuntionService();
        PortFuntionDelegate portType = service.getPortFuntionPort();

         String str = "   ︵  ᠲᠤᠰ ᠰᠣᠨᠢᠨ ᠤ᠋ ᠴᠢᠮᠡᠭᠡ ᠪᠡᠷ︶ ᠥᠪᠥᠷ ᠮᠣᠩᠭᠣᠯ ᠤ᠋ᠨ ᠥᠪᠡᠷᠲᠡᠭᠡᠨ ᠵᠠᠰᠠᠬᠤ ᠣᠷᠣᠨ ᠤ᠋ ᠣᠯᠠᠨ ᠠᠢᠮᠠᠭ ᠤ᠋ᠨ ᠮᠠᠯ ᠲᠠᠷᠢᠶᠠᠯᠠᠩ ᠤ᠋ᠨ ᠭᠠᠵᠠᠷᠤᠨ ᠳᠠᠷᠤᠭ᠎ᠠ ᠨᠠᠷᠤᠨ ᠭᠤᠷᠪᠠᠳᠤᠭᠠᠷ ᠤᠳᠠᠭ᠎ᠠ ᠶ᠋ᠢᠨ ᠬᠤᠷᠠᠯ᠂ ᠠᠷᠪᠠᠨ ᠬᠣᠶᠠᠷ ᠡᠳᠦᠷ ᠬᠤᠷᠠᠯᠳᠤᠵᠤ ᠠᠷᠪᠠᠨ ᠨᠢᠭᠡᠳᠦᠭᠡᠷ ᠰᠠᠷᠠᠢᠨ ᠠᠷᠪᠠᠨ ᠬᠣᠶᠠᠷ ᠲᠤ᠌ ᠠᠮᠵᠢᠯᠲᠠᠲᠠᠢ ᠪᠠᠷ ᠲᠡᠭᠦᠰᠪᠡ᠃ ᠬᠤᠷᠠᠯ ᠳ᠋ᠤ᠌ ᠥᠪᠥᠷ ᠮᠣᠩᠭᠣᠯ ᠤ᠋ᠨ ᠪᠦᠬᠦ ᠣᠷᠣᠨ ᠤ᠋ ᠡᠨᠡ ᠵᠢᠯ ᠦ᠋ᠨ ᠮᠠᠯ ᠲᠠᠷᠢᠶᠠᠯᠠᠩ ᠤ᠋ᠨ ᠦᠢᠯᠡᠳᠪᠦᠷᠢᠯᠡᠯ ᠦ᠋ᠨ ᠠᠵᠢᠯ ᠢ᠋ ᠳ᠋ᠦᠩᠨᠡᠭᠰᠡᠨ ᠪᠥᠭᠡᠳ ᠢᠷᠡᠬᠦ ᠵᠢᠯ ᠦ᠋ᠨ ᠦᠢᠯᠡᠳᠪᠦᠷᠢᠯᠡᠯ ᠦ᠋ᠨ ᠴᠢᠭᠯᠡᠯᠲᠡ ᠶ᠋ᠢ ᠶᠠᠷᠢᠯᠴᠠᠪᠠ᠃ ᠳ᠋ᠦᠩᠨᠡᠯᠲᠡ ᠶ᠋ᠢᠨ ᠳᠣᠲᠣᠷ᠎ᠠ ᠳᠤᠷᠠᠳᠤᠭᠰᠠᠨ ᠨᠢ᠄ᠡᠨᠡ ᠵᠢᠯ ᠨᠡᠯᠢᠶᠡᠳ ᠭᠠᠵᠠᠷ ᠲᠤ᠌ ᠭᠠᠩᠳᠠᠭᠰᠠᠨ ᠪᠣᠯᠪᠠᠴᠤ ᠣᠯᠠᠨ ᠱᠠᠲᠤᠨ ᠤ᠋ ᠨᠠᠮ ᠵᠠᠰᠠᠭ ᠠ᠋ᠴᠠ ᠢᠳᠡᠪᠬᠢ ᠲᠡᠢ ᠪᠡᠷ ᠤᠳᠤᠷᠢᠳᠤᠭᠰᠠᠨ ᠪᠠ ᠥᠷᠭᠡᠨ ᠣᠯᠠᠨ ᠠᠷᠠᠳ ᠲᠦᠮᠡᠨ ᠭᠠᠷᠤᠯᠲᠠ ᠪᠠᠨ ᠨᠡᠮᠡᠭᠳᠡᠭᠦᠯᠦᠨ ᠮᠠᠯ ᠢ᠋ᠶ᠋ᠠᠨ ᠦᠷᠡᠵᠢᠭᠦᠯᠬᠦ ᠤᠯᠤᠰ ᠢ᠋ᠶ᠋ᠠᠨ ᠬᠠᠢᠷᠠᠯᠠᠬᠤ ᠤᠷᠤᠯᠳᠤᠭᠠᠨ ᠤ᠋ ᠴᠢᠷᠮᠠᠢᠯᠭ᠎ᠠ ᠪᠠᠷ ᠮᠠᠯ ᠲᠠᠷᠢᠶᠠᠯᠠᠩ ᠤ᠋ᠨ ᠦᠢᠯᠡᠳᠪᠦᠷᠢᠯᠡᠯ ᠦ᠋ᠨ ᠲᠠᠯ᠎ᠠ ᠪᠠᠷ ᠨᠡᠯᠢᠶᠡᠳ ᠠᠮᠢᠵᠢᠯᠲᠠ ᠣᠯᠵᠠᠢ᠃ ᠵᠢᠱᠢᠶᠠᠯᠠᠪᠠᠯ᠄ᠪᠦᠬᠦ ᠣᠷᠣᠨ ᠳ᠋ᠤ᠌ ᠭᠤᠷᠪᠠᠨ ᠵᠠᠭᠤᠨ ᠶᠡᠷᠡᠨ ᠶᠢᠰᠦᠨ ᠤᠰᠤᠨ ᠰᠤᠪᠠᠭ ᠪᠠ ᠬᠣᠶᠠᠷ ᠲᠦᠮᠡᠨ ᠳᠣᠯᠣᠭᠠᠨ ᠵᠠᠭᠤ ᠲᠠᠪᠢᠨ ᠨᠢᠭᠡᠨ ᠬᠤᠳᠳᠤᠭ ᠮᠠᠯᠲᠠᠵᠤ᠂ ᠬᠣᠶᠠᠷ ᠲᠦᠮᠡᠨ ᠡᠳᠦᠷᠦᠨ ᠲᠠᠷᠢᠶ᠎ᠠ ᠶ᠋ᠢ ᠤᠰᠤᠯᠠᠭᠠᠳ ᠡᠳᠡᠭᠡᠷ ᠲᠠᠷᠢᠶᠠᠨ ᠤ᠋ ᠶᠡᠷᠦ ᠶ᠋ᠢᠨ ᠬᠤᠷᠢᠶᠠᠯᠲᠠ ᠶ᠋ᠢ ᠪᠠᠲᠤᠯᠠᠵᠠᠢ᠃ ᠭᠡᠪᠡᠴᠦ ᠵᠠᠷᠢᠮ ᠭᠠᠵᠠᠷ ᠭᠠᠩ ᠭᠠᠮᠱᠢᠭ ᠲᠤ᠌ ᠬᠦᠨᠳᠦ ᠰᠦᠷᠬᠡᠢ ᠳᠠᠭᠠᠷᠢᠭᠳᠠᠵᠤ ᠬᠤᠷᠢᠶᠠᠯᠲᠠ ᠨᠢ ᠮᠠᠱᠢ ᠴᠥᠭᠡᠬᠡᠨ ᠤᠴᠢᠷ ᠠ᠋ᠴᠠ᠂ ᠣᠯᠠᠨ ᠲᠦᠮᠡᠨ ᠢ᠋ ᠵᠣᠬᠢᠶᠠᠨ ᠪᠠᠢᠭᠤᠯᠵᠤ ᠦᠢᠯᠡᠳᠪᠦᠷᠢᠯᠡᠯ ᠢ᠋ᠶ᠋ᠡᠷ ᠥᠪᠡᠷᠲᠡᠭᠡᠨ ᠲᠡᠩᠬᠡᠷᠡᠬᠦ ᠬᠥᠳᠡᠯᠭᠡᠭᠡᠨ ᠢ᠋ ᠶᠡᠬᠡ ᠪᠡᠷ ᠥᠷᠨᠢᠭᠦᠯᠬᠦ ᠶ᠋ᠢ ᠲᠤᠰ ᠬᠤᠷᠠᠯ ᠠ᠋ᠴᠠ ᠵᠢᠭᠠᠵᠤ ᠭᠠᠷᠭᠠᠭᠰᠠᠨ ᠤ᠋ ᠬᠠᠮᠲᠤ ᠭᠠᠮᠱᠢᠭ ᠥᠬᠡᠢ  ᠣᠷᠣᠨ  ᠪᠠ ᠬᠥᠩᠭᠡᠨ ᠭᠠᠮᠱᠢᠭᠳᠠᠭᠰᠠᠨ ᠣᠷᠣᠨ ᠤ᠋ ᠠᠷᠠᠳ ᠲᠦᠮᠡᠨ ᠢ᠋ ᠳᠠᠢᠴᠢᠯᠠᠨ ᠬᠥᠳᠡᠯᠭᠡᠵᠦ᠂ ᠢᠯᠡᠭᠦᠦ ᠠᠮᠤ ᠶ᠋ᠢ ᠨᠢ ᠵᠠᠰᠠᠭ ᠤ᠋ᠨ ᠣᠷᠳᠣᠨ ᠳ᠋ᠤ᠌ ᠬᠤᠳᠠᠯᠳᠤᠭᠤᠯᠵᠤ᠂ ᠡᠭᠦᠨ ᠢ᠋ ᠬᠦᠨᠳᠦ ᠭᠠᠮᠱᠢᠭᠳᠠᠭᠰᠠᠨ ᠣᠷᠣᠨ ᠳ᠋ᠤ᠌ ᠲᠤᠬᠢᠷᠠᠭᠤᠯᠠᠨ ᠥᠭᠭᠦᠭᠡᠳ ᠭᠠᠮᠱᠢᠭᠲᠤ ᠠᠷᠠᠳ ᠢ᠋ ᠭᠠᠮᠱᠢᠭ ᠠ᠋ᠴᠠ ᠭᠡᠲᠦᠯᠦᠭᠦᠯᠦᠨ᠎ᠡ ᠭᠡᠵᠦ ᠲᠤᠷᠠᠳᠤᠵᠠᠢ᠃ ᠮᠠᠯᠵᠢᠬᠤ ᠦᠢᠯᠡᠳᠪᠦᠷᠢᠯᠡᠯ ᠦ᠋ᠨ ᠲᠠᠯ᠎ᠠ ᠪᠠᠷ ᠪᠦᠬᠦ ᠣᠷᠣᠨ ᠤ᠋ ᠮᠠᠯ ᠤ᠋ᠨ ᠦᠷᠡᠵᠢᠯᠲᠡ ᠨᠢ ᠵᠠᠭᠤᠨ ᠤ᠋ ᠬᠣᠷᠢᠨ ᠬᠤᠪᠢ ᠬᠦᠷᠴᠦ᠂ ᠡᠭᠦᠷᠭᠡ ᠡᠴᠡ ᠪᠡᠨ ᠨᠢᠭᠡ ᠳᠠᠬᠢᠨ ᠳᠠᠪᠠᠭᠤᠯᠵᠠᠢ᠃ ᠲᠥᠯ ᠢ᠋ ᠪᠣᠢᠵᠢᠭᠤᠯᠤᠭᠰᠠᠨ ᠬᠡᠮᠵᠢᠶ᠎ᠡ ᠨᠢ ᠵᠠᠭᠤᠨ ᠤ᠋ ᠵᠢᠷᠠᠨ ᠠ᠋ᠴᠠ ᠳᠡᠭᠡᠭᠱᠢ ᠬᠤᠪᠢ ᠬᠦᠷᠴᠦ᠂ ᠬᠢᠵᠢᠭ ᠦ᠋ᠨ ᠠᠶᠤᠯ ᠨᠢ ᠨᠢᠳᠤᠨᠤᠨ ᠵᠢᠯ ᠡᠴᠡ ᠵᠠᠭᠤᠨ ᠤ᠋ ᠨᠠᠢ᠍ᠮᠠᠨ ᠬᠤᠪᠢ ᠪᠠᠭᠠᠰᠴᠠᠢ᠂ ᠴᠢᠨᠣ᠎ᠠ ᠶ᠋ᠢᠨ ᠠᠶᠤᠯ ᠵᠠᠭᠤᠨ ᠤ᠋ ᠶᠢᠰᠦ ᠪᠥᠭᠡᠳ ᠳᠥᠷᠪᠡᠨ ᠬᠤᠪᠢ ᠴᠥᠭᠡᠷᠡᠵᠡᠢ᠃ ᠭᠡᠪᠡᠴᠦ ᠤᠰᠤ ᠪᠣᠷᠣᠭᠠᠨ ᠬᠣᠪᠣᠷ ᠲᠤᠯᠠ ᠡᠪᠡᠰᠦ ᠪᠡᠯᠴᠢᠭᠡᠷ᠂ ᠮᠠᠯ ᠤ᠋ᠨ ᠬᠦᠴᠦ ᠲᠠᠷᠭᠤ ᠮᠠᠭᠤ ᠪᠥᠭᠡᠳ ᠲᠥᠯ ᠮᠠᠯ ᠪᠠᠰᠠ ᠣᠯᠠᠨ ᠪᠠᠢᠨ᠎ᠠ᠃ ᠡᠢᠮᠦ ᠪᠣᠯᠬᠣᠷ ᠮᠠᠯᠵᠢᠬᠤ ᠣᠷᠣᠨ ᠤ᠋ ᠤᠳᠤᠷᠢᠳᠤᠯᠭ᠎ᠠ ᠪᠠᠨ ᠴᠢᠩᠭᠠᠳᠭᠠᠨ ᠡᠪᠦᠯ ᠦ᠋ᠨ ᠲᠡᠰᠬᠢᠮ᠎ᠡ ᠬᠦᠢᠲᠡᠨ ᠡᠴᠡ ᠤᠷᠢᠳ ᠮᠠᠯᠴᠢᠳ ᠢ᠋ ᠵᠣᠬᠢᠶᠠᠨ ᠪᠠᠢᠭᠤᠯᠵᠤ ᠡᠪᠦᠯᠵᠢᠬᠦ ᠠᠵᠢᠯ ᠢ᠋ᠶ᠋ᠠᠨ ᠰᠠᠢᠨ ᠢ᠋ᠶ᠋ᠠᠷ ᠬᠢᠵᠦ᠂ ᠬᠣᠢᠲᠤ ᠵᠢᠯ ᠦ᠋ᠨ ᠮᠠᠯ ᠢ᠋ᠶ᠋ᠠᠨ ᠦᠷᠡᠵᠢᠭᠦᠯᠬᠦ ᠰᠠᠭᠤᠷᠢ ᠶ᠋ᠢ ᠪᠡᠯᠡᠳᠭᠡᠬᠦ ᠬᠡᠷᠡᠭᠲᠡᠢ᠃ ᠬᠣᠢᠲᠤ ᠵᠢᠯ ᠦ᠋ᠨ ᠲᠠᠷᠢᠶᠠᠯᠠᠩ ᠤ᠋ᠨ ᠦᠢᠯᠡᠳᠪᠦᠷᠢᠯᠡᠯ ᠦ᠋ᠨ ᠴᠢᠭᠯᠡᠯᠡᠳᠡ ᠪᠣᠯ ᠨᠠᠷᠢᠨ ᠨᠢᠮᠪᠠᠢ ᠳᠠᠷᠤᠭᠠᠯᠠᠵᠤ᠂ ᠮᠡᠷᠭᠡᠵᠢᠯ ᠢ᠋ᠶ᠋ᠡᠨ ᠰᠠᠢᠵᠢᠷᠠᠭᠤᠯᠤᠨ ᠳᠡᠭᠡᠭᠱᠢᠯᠡᠭᠦᠯᠵᠦ᠂ ᠦᠢ ᠣᠯᠠᠨ ᠠᠷᠠᠳ ᠲᠦᠮᠡᠨ ᠢ᠋ ᠵᠣᠬᠢᠶᠠᠨ ᠪᠠᠢᠭᠤᠯᠵᠤ ᠪᠠᠢᠭᠠᠯᠢ ᠶ᠋ᠢᠨ ᠭᠠᠮᠱᠢᠭ ᠢ᠋ ᠰᠡᠷᠭᠡᠢᠯᠡᠨ ᠤᠰᠠᠳᠭᠠᠵᠤ᠂ ᠭᠠᠷᠤᠯᠲᠠ ᠶ᠋ᠢᠨ ᠬᠡᠮᠵᠢᠶ᠎ᠡ ᠶ᠋ᠢ ᠨᠡᠮᠡᠭᠳᠡᠭᠦᠯᠦᠨ ᠠᠮᠤ ᠠᠷᠪᠢᠨ ᠬᠤᠷᠢᠶᠠᠬᠤ ᠶ᠋ᠢ ᠭᠣᠣᠯ ᠪᠣᠯᠭᠠᠨ᠎ᠠ᠃ ᠲᠠᠷᠢᠶ᠎ᠠ ᠤᠰᠤᠯᠠᠬᠤ ᠨᠥᠬᠥᠴᠡᠯ ᠲᠡᠢ ᠣᠷᠣᠨ ᠳ᠋ᠤ᠌ ᠲᠠᠷᠢᠶ᠎ᠠ ᠤᠰᠤᠯᠠᠬᠤ ᠠᠵᠢᠯ ᠢ᠋ ᠶᠡᠬᠡ ᠪᠡᠷ ᠥᠷᠨᠢᠭᠦᠯᠦᠨ᠎ᠡ᠃ ᠮᠠᠯᠵᠢᠬᠤ ᠣᠷᠣᠨ ᠳ᠋ᠤ᠌ ᠮᠠᠯ ᠢ᠋ᠶ᠋ᠠᠨ ᠬᠠᠮᠠᠭᠠᠯᠠᠬᠤ ᠪᠠ ᠦᠷᠡᠵᠢᠭᠦᠯᠬᠦ ᠠᠵᠢᠯ ᠢ᠋ ᠰᠠᠢᠵᠢᠷᠠᠭᠤᠯᠤᠨ ᠴᠢᠩᠭᠠᠳᠭᠠᠬᠤ ᠶ᠋ᠢ ᠭᠣᠣᠯ ᠪᠣᠯᠭᠠᠵᠤ ᠵᠢᠴᠢ ᠮᠠᠯ ᠤ᠋ᠨ ᠴᠢᠨᠠᠷ ᠢ᠋ ᠠᠭᠠᠵᠢᠮ ᠢ᠋ᠶ᠋ᠠᠷ ᠰᠠᠢᠵᠢᠷᠠᠭᠤᠯᠤᠨ᠎ᠠ᠂ ᠴᠢᠯᠥᠭᠡ ᠲᠡᠢ ᠪᠡᠷ ᠮᠠᠯᠵᠢᠬᠤ ᠪᠣᠳᠣᠯᠭ᠎ᠠ ᠶ᠋ᠢ ᠵᠥᠪ ᠢ᠋ᠶ᠋ᠡᠷ ᠶᠠᠪᠤᠭᠤᠯᠵᠤ᠂ ᠬᠢᠵᠢᠭ ᠢ᠋ ᠰᠡᠷᠭᠡᠢᠯᠡᠬᠦ᠂ ᠴᠢᠨᠣ᠎ᠠ ᠳ᠋ᠤ᠌ ᠠᠪᠠᠯᠠᠬᠤ ᠶ᠋ᠢ ᠢᠳᠡᠪᠬᠢᠲᠡᠢ ᠪᠡᠷ ᠶᠠᠪᠤᠭᠤᠯᠤᠨ᠂ ᠪᠠᠢᠭᠠᠯᠢ ᠶ᠋ᠢᠨ ᠭᠠᠮᠱᠢᠭ ᠢ᠋ ᠰᠡᠷᠭᠡᠢᠯᠡᠨ ᠤᠰᠠᠳᠭᠠᠵᠤ᠂ ᠮᠠᠯᠵᠢᠬᠤ ᠠᠷᠭ᠎ᠠ ᠶ᠋ᠢ ᠰᠠᠢᠵᠢᠷᠠᠭᠤᠯᠤᠨ᠂ ᠮᠠᠯ ᠠᠵᠤ ᠠᠬᠤᠢ ᠶ᠋ᠢ ᠶᠡᠬᠡ ᠪᠡᠷ ᠬᠥᠭᠵᠢᠭᠦᠯᠦᠨ᠎ᠡ᠃";
        System.out.println(portType. fileITMDTrans(str));

        StringBuffer result = new StringBuffer();
//         String[] strs = str.split(" ");

//        for (int i = 0; i < strs.length; i++) {
//            String s = null;
//            System.out.println(strs[i]);
//            try {
//                s = portFuntionDelegate.mongoliantoITMDTrans(strs[i]);
//            } catch (IOException_Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println(s);
//            result.append(s+" ");
//        }

//        System.out.println(strs);

    }
}
