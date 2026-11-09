from PIL import Image, ImageDraw

size = 256
img = Image.new("RGBA", (size, size), (37, 99, 235, 255))
d = ImageDraw.Draw(img)
d.rounded_rectangle((12, 12, 244, 244), radius=54, fill=(37, 99, 235, 255))
d.ellipse((63, 58, 193, 188), fill=(255, 255, 255, 255))
d.ellipse((95, 90, 161, 156), fill=(37, 99, 235, 255))
d.arc((52, 48, 204, 200), 210, 330, fill=(255, 255, 255, 255), width=10)
img.save("Foodly.ico", sizes=[(256, 256), (128, 128), (64, 64), (32, 32), (16, 16)])
