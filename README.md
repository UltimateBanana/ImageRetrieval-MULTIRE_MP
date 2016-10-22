# ImageRetrieval-MULTIRE_MP
MULTIRE MP1 - Image Retrieval

Issues Encountered and How To Fix:

1. Access Restriction on JPEGImageDecoder (Image.java)
- Go to the Build Path settings in the project properties.
- Go to Configure Build Path > Libraries
- Remove "JRE System Library"
- Click "Apply"
- Click "Add Library"
- Select "JRE System Library", click "Next", select "Workspace Default..."
- Click "Finish", click "OK"

